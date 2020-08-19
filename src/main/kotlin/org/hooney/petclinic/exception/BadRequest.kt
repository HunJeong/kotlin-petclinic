package org.hooney.petclinic.exception

import org.springframework.http.HttpStatus

sealed class BadRequest(message: String="Bad Request"): PetClinicException(message, HttpStatus.BAD_REQUEST)