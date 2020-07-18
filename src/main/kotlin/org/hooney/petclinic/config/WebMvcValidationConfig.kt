package org.hooney.petclinic.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@EnableWebMvc
@Configuration
class WebMvcValidationConfig: WebMvcConfigurer {
    @Bean
    fun methodValidationPostProcessor() = MethodValidationPostProcessor()
}