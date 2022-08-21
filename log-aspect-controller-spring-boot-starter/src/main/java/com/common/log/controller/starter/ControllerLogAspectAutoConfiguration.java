package com.common.log.controller.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@ConditionalOnWebApplication
@Configuration
public class ControllerLogAspectAutoConfiguration {

    @Resource
    HttpServletRequest request;

    @Bean
    public ControllerLogAspect controllerLogAspect(){
        return new ControllerLogAspect(request);
    }

}
