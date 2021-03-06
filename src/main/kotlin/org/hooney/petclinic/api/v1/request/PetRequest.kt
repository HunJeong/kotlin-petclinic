package org.hooney.petclinic.api.v1.request

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class PetCreateRequest(
        @get:NotBlank val name: String = "",
        @get:NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") val birthDate: LocalDate? = null,
        @get:NotBlank val type: String = ""
)