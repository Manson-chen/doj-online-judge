package com.jd.dojbackendjudgeservice.judge.strategy;

import com.jd.dojbackendmodel.model.codesandbox.JudgeInfo;
import com.jd.dojbackendmodel.model.dto.question.JudgeCase;
import com.jd.dojbackendmodel.model.entity.Question;
import com.jd.dojbackendmodel.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文（用于定义在策略中需要传递的参数）
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;
}
