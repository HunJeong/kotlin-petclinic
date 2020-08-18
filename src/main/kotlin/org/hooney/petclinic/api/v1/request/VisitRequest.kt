package org.hooney.petclinic.api.v1.request

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.validation.constraints.NotBlank

class VisitCreateRequest(
        @get:NotBlank val description: String = "",
        @get:DateTimeFormat(pattern = "yyyy-MM-dd") val date: LocalDate = LocalDate.now()
)

class VisitPutRequest(
        @get:NotBlank val description: String = "",
        @get:DateTimeFormat(pattern = "yyyy-MM-dd") val date: LocalDate? = null
)