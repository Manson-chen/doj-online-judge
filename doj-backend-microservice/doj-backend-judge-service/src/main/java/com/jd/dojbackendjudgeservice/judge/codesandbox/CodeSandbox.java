package com.jd.dojbackendjudgeservice.judge.codesandbox;


import com.jd.dojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.jd.dojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandbox {

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
