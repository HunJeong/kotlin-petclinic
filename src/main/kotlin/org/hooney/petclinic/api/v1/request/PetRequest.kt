package org.hooney.petclinic.api.v1.request

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.validation.constraints.NotNull

class PetRequest {

    class PetCreateRequest(
        @get:NotNull var name: String? = null,
        @get:NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") var birthDate: LocalDate? = null,
        @get:NotNull var type: String? = null
    )

}