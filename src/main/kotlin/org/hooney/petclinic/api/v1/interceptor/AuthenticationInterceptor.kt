package org.hooney.petclinic.api.v1.interceptor

import org.hooney.petclinic.annotation.Authorized
import org.hooney.petclinic.exception.LoginFailException
import org.hooney.petclinic.service.AccessTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationInterceptor(
    val accessTokenService: AccessTokenService
): HandlerInterceptorAdapter() {

    companion object {
        val TOKEN_PATTERN = Regex("Bearer ")
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod
            && (handler.method.isAnnotationPresent(Authorized::class.java) || handler.method.declaringClass.isAnnotationPresent(Authorized::class.java) )
        ) {
            val token = request.getHeader(HttpHeaders.AUTHORIZATION)?.replace(TOKEN_PATTERN, "") ?: throw LoginFailException()
            val accessToken = accessTokenService.findByToken(token) ?: throw LoginFailException()
            val owner = accessToken.owner

            request.setAttribute("owner", owner)
        }
        return true
    }
}