package org.hooney.petclinic.exceptions

import java.lang.RuntimeException

open class PetClinicException(message: String?): RuntimeException(message)