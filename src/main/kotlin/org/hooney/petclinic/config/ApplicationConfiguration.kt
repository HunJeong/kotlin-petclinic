package org.hooney.petclinic.config

import org.hooney.petclinic.config.filter.AuditingFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("org.hooney.petclinic")
class ApplicationConfiguration {

    @Bean
    fun auditingFilterRegistrationBean(): FilterRegistrationBean<AuditingFilter> {
        val registration: FilterRegistrationBean<AuditingFilter> = FilterRegistrationBean()
        val filter = AuditingFilter()
        registration.filter = filter
        registration.order = Int.MAX_VALUE
        registration.urlPatterns = listOf("/api/*")
        return registration
    }

}