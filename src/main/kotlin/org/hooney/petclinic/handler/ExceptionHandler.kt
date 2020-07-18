package org.hooney.petclinic.handler

import org.hooney.petclinic.api.v1.response.MessageResponse
import org.hooney.petclinic.entity.Pet
import org.hooney.petclinic.exceptions.PetClinicException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.validation.ConstraintViolationException

@ControllerAdvice
class ExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun handleInvalidParameter(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        val message = MessageResponse(success = false, message = ex.message ?: "")
        return handleExceptionInternal(ex, message, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

    @ExceptionHandler(value = [PetClinicException::class])
    fun handlePetClinicException(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        val petClinicException = ex as PetClinicException
        val message = MessageResponse(success = false, message = petClinicException.message ?: "")
        return handleExceptionInternal(petClinicException, message, HttpHeaders(), petClinicException.httpStatus, request)
    }

}