package com.jd.dojbackenduserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.jd.dojbackenduserservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.jd")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.jd.dojbackendserviceclient.service"})
public class DojBackendUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DojBackendUserServiceApplication.class, args);
	}

}
