package com.common.log.feign.starter;

import feign.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnClass(FeignClient.class)
@Configuration
public class FeignLogAspectAutoConfiguration {

    @Bean
    Logger.Level loggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    Logger feignLogger() {
        return new FeignLogger();
    }

}
