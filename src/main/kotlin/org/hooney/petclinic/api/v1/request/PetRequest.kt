package org.hooney.petclinic.api.v1.request

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.validation.constraints.NotNull

class PetRequest {

    class PetCreateRequest(
        @get:NotNull val name: String? = null,
        @get:NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") val birthDate: LocalDate? = null,
        @get:NotNull val type: String? = null
    )

}