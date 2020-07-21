package org.hooney.petclinic.exceptions

import org.springframework.http.HttpStatus

sealed class BadRequest(message: String="Bad Request"): PetClinicException(message, HttpStatus.BAD_REQUEST)