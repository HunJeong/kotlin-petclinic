package org.hooney.petclinic.api.v1

import org.hooney.petclinic.api.v1.request.VisitCreateRequest
import org.hooney.petclinic.api.v1.request.VisitPutRequest
import org.hooney.petclinic.api.v1.response.VisitResponse
import org.hooney.petclinic.service.VisitService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class VisitController(val visitService: VisitService) {

    @GetMapping("/api/v1/owners/{ownerId}/pets/{petId}/visits")
    fun getVisits(
        @PathVariable ownerId: Long,
        @PathVariable petId: Long
    ) = visitService.getVisits(ownerId, petId).map { VisitResponse(it) }

    @PostMapping("/api/v1/owners/{ownerId}/pets/{petId}/visits")
    @ResponseStatus(HttpStatus.CREATED)
    fun postVisits(
        @PathVariable ownerId: Long,
        @PathVariable petId: Long,
        @RequestBody @Valid body: VisitCreateRequest
    ) = VisitResponse(visitService.createVisit(ownerId, petId, body.description, body.date))

    @PutMapping("/api/v1/owners/{ownerId}/pets/{petId}/visits/{visitId}")
    fun putVisits(
        @PathVariable ownerId: Long,
        @PathVariable petId: Long,
        @PathVariable visitId: Long,
        @RequestBody @Valid body: VisitPutRequest
    ) = VisitResponse(visitService.putVisit(ownerId, petId, visitId, body.description, body.date))

}