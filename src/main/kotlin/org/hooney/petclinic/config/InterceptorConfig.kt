package org.hooney.petclinic.config

import org.hooney.petclinic.api.v1.interceptor.AuthenticationInterceptor
import org.hooney.petclinic.service.AccessTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class InterceptorConfig: WebMvcConfigurer {

    @Autowired
    lateinit var accessTokenService: AccessTokenService

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(AuthenticationInterceptor(accessTokenService)).addPathPatterns("/api/v1/*")
    }

}