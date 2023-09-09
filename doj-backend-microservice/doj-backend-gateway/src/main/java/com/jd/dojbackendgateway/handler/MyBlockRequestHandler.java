package com.jd.dojbackendgateway.handler;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.jd.dojbackendcommon.common.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;

public class MyBlockRequestHandler implements BlockRequestHandler {
    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable t) {
        String exceptionClassName = t.getClass().getName();
        HashMap<String, String> map = new HashMap<>();
        if(exceptionClassName.equals("com.alibaba.csp.sentinel.slots.block.degrade.DegradeException")){
            map.put("code", String.valueOf(ErrorCode.OPERATION_ERROR.getCode()));
            map.put("message", "服务降级！");
        }else if(exceptionClassName.equals("com.alibaba.csp.sentinel.slots.block.flow.FlowException")){ // 流量控制异常的异常类
            map.put("code", String.valueOf(ErrorCode.OPERATION_ERROR.getCode()));
            map.put("message", "服务流量过大!");
        }else if(exceptionClassName.equals("com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException")){ // 热点参数限流异常的异常类
            map.put("code", String.valueOf(ErrorCode.OPERATION_ERROR.getCode()));
            map.put("message", "提交题目人数过多!");
        } else{
            map.put("code", String.valueOf(ErrorCode.OPERATION_ERROR.getCode()));
            map.put("message", exceptionClassName);
        }

        return ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(map));
    }
}
