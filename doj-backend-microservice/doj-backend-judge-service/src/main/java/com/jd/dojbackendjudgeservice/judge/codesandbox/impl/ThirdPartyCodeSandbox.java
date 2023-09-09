package com.jd.dojbackendjudgeservice.judge.codesandbox.impl;


import com.jd.dojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.jd.dojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.jd.dojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * 第三方代码沙箱（调用网上现成的的代码沙箱）
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
