package com.renju.data.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class Configs {

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}