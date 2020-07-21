package org.hooney.petclinic.api.v1.response

import org.hooney.petclinic.entity.Visit
import java.time.format.DateTimeFormatter

class VisitResponse(visit: Visit) {
    val petId = visit.petId
    val description = visit.description
    val date = visit.date?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}