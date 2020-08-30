package org.hooney.petclinic.api.v1.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class SignupRequest(
    @get:Email val email: String = "",
    @get:Size(min = 10, max = 16) val password: String = "",
    @get:NotBlank val firstName: String = "",
    @get:NotBlank val lastName: String = "",
    @get:NotBlank val address: String = "",
    val city: String? = null,
    @get:Size(min = 9, max = 13) val telephone: String = ""
)

class SigninRequest(
    @get:Email val email: String = "",
    @get:Size(min = 10, max = 16) val password: String = ""
)