package org.hooney.petclinic.handler

import org.hooney.petclinic.api.v1.response.MessageResponse
import org.hooney.petclinic.exception.PetClinicException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler: ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val message = MessageResponse(success = false, message = ex.message ?: "")
        return handleExceptionInternal(ex, message, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

    @ExceptionHandler(value = [PetClinicException::class])
    fun handlePetClinicException(ex: PetClinicException, request: WebRequest): ResponseEntity<Any> {
        val message = MessageResponse(success = false, message = ex.message ?: "")
        return handleExceptionInternal(ex, message, HttpHeaders(), ex.httpStatus, request)
    }

}