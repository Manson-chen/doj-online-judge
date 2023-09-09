package com.jd.dojbackendserviceclient.service;

import com.jd.dojbackendmodel.model.entity.Question;
import com.jd.dojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
* @author jiandongchen
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2023-08-28 13:38:12
*/
@FeignClient(name = "doj-backend-question-service", path = "/api/question/inner")
public interface QuestionFeignClient {

    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("questionId") long questionId);

    @GetMapping("/question_submit/get/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId);

    @PostMapping("/question_submit/update")
    boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit);

    @PostMapping("/question/accepted_num/update")
    boolean updateQuestionAcceptedNum(@RequestBody Question question);
}
