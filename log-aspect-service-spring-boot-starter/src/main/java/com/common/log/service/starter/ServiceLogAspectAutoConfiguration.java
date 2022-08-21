package com.common.log.service.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceLogAspectAutoConfiguration {

    @Bean
    ServiceLogAspect serviceLogAspect() {
        return new ServiceLogAspect();
    }

}
