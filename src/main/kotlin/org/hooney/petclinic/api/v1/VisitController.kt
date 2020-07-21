package org.hooney.petclinic.api.v1

import org.hooney.petclinic.api.v1.response.VisitResponse
import org.hooney.petclinic.service.VisitService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class VisitController(val visitService: VisitService) {

    @GetMapping("/api/v1/owners/{ownerId}/pets/{petId}/visits")
    fun getVisits(
        @PathVariable ownerId: Long,
        @PathVariable petId: Long
    ) = visitService.getVisits(ownerId, petId).map { VisitResponse(it) }

}