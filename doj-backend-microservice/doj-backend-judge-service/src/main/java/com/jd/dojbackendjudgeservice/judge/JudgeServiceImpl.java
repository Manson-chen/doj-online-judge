package com.jd.dojbackendjudgeservice.judge;

import cn.hutool.json.JSONUtil;
import com.jd.dojbackendcommon.common.ErrorCode;
import com.jd.dojbackendcommon.exception.BusinessException;
import com.jd.dojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.jd.dojbackendjudgeservice.judge.codesandbox.CodeSandboxFactory;
import com.jd.dojbackendjudgeservice.judge.codesandbox.CodeSandboxProxy;
import com.jd.dojbackendjudgeservice.judge.strategy.JudgeContext;
import com.jd.dojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.jd.dojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.jd.dojbackendmodel.model.codesandbox.JudgeInfo;
import com.jd.dojbackendmodel.model.dto.question.JudgeCase;
import com.jd.dojbackendmodel.model.entity.Question;
import com.jd.dojbackendmodel.model.entity.QuestionSubmit;
import com.jd.dojbackendmodel.model.enums.JudgeInfoMessageEnum;
import com.jd.dojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.jd.dojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionFeignClient questionFeignClient;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 1. 传入题目的提交id，获取到对应的题目、提交信息（包含diamanté、编程语言等）
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionFeignClient.getQuestionById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        // 2. 如果题目的提交状态不为等待中，就不用重复执行了
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        // 3. 更改判题（题目提交）的状态为 "判题中"，防止重复执行
        boolean update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }

        // 4. 调用沙箱，获取到执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        // 获取输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        // 封装请求
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        // 获取执结果
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();

        // 5. 根据沙箱的执行结果，设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // todo 如果判题信息为通过，增加 question 的 通过数
        String judgeStatus = judgeInfo.getMessage();
        if (JudgeInfoMessageEnum.ACCEPTED.getValue().equals(judgeStatus)) {
            int acceptedNum = question.getAcceptedNum();
            question.setAcceptedNum(acceptedNum + 1);
            boolean updateAcceptedNum = questionFeignClient.updateQuestionAcceptedNum(question);
            if (!updateAcceptedNum) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目通过数更新错误");
            }
        }

        // 6. 修改数据库中的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        // todo 这里要修改成 questionSubmitId
        QuestionSubmit questionSubmitResult = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        return questionSubmitResult;
    }
}
