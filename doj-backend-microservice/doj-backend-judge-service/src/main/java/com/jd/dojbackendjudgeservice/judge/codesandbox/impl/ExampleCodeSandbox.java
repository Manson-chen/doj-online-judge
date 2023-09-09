package com.jd.dojbackendjudgeservice.judge.codesandbox.impl;


import com.jd.dojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.jd.dojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.jd.dojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.jd.dojbackendmodel.model.codesandbox.JudgeInfo;
import com.jd.dojbackendmodel.model.enums.JudgeInfoMessageEnum;
import com.jd.dojbackendmodel.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 示例代码沙箱（仅为了跑通业务流程）
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        List<String> inputList = executeCodeRequest.getInputList();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
