package org.hooney.petclinic.exception

import org.springframework.http.HttpStatus
import java.lang.RuntimeException

open class PetClinicException(message: String?, val httpStatus: HttpStatus): RuntimeException(message)