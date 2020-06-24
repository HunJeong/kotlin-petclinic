package org.hooney.petclinic.config.filter

import org.springframework.web.filter.GenericFilterBean
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuditingFilter: GenericFilterBean() {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val start: Long = Date().time
        chain?.doFilter(request, response)
        val elapsed: Long = Date().time - start

        val _request = request as HttpServletRequest
        val _response = response as HttpServletResponse
        logger.info("Request[status=${_response.status} uri=${_request.requestURI}, method=${_request.method}] completed in $elapsed ms")
    }
}