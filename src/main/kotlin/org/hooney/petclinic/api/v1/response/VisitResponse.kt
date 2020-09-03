package org.hooney.petclinic.api.v1.response

import org.hooney.petclinic.annotation.NoArgConstructor
import org.hooney.petclinic.entity.Visit
import java.time.format.DateTimeFormatter

@NoArgConstructor
class VisitResponse(visit: Visit) {
    val petId = visit.petId
    val description = visit.description
    val date = visit.date?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}