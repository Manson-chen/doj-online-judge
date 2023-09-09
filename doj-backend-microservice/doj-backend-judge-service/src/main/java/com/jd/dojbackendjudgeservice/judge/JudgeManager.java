package com.jd.dojbackendjudgeservice.judge;

import com.jd.dojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.jd.dojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.jd.dojbackendjudgeservice.judge.strategy.JudgeContext;
import com.jd.dojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.jd.dojbackendmodel.model.codesandbox.JudgeInfo;
import com.jd.dojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext){
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
