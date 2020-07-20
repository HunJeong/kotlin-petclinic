package org.hooney.petclinic.api.v1

import org.hooney.petclinic.api.v1.request.OwnerRequest
import org.hooney.petclinic.api.v1.response.OwnerResponse
import org.hooney.petclinic.exceptions.OwnerNotFoundException
import org.hooney.petclinic.service.OwnerService
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Size

@RestController
class OwnerController(val ownerService: OwnerService) {

    @GetMapping("/api/v1/owners", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getOwners() = this.ownerService.getOwners().map { OwnerResponse(it) }

    @GetMapping("/api/v1/owners/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getOwner(@PathVariable("id") id: Long) = this.ownerService.getOwner(id)?.let { OwnerResponse(it) } ?: throw OwnerNotFoundException()

    @PostMapping("/api/v1/owners", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun createOwner(
        @RequestBody @Valid body: OwnerRequest.OwnerCreateRequest
    ) = ownerService.createOwner(body.firstName!!, body.lastName!!, body.address!!, body.city, body.telephone!!).let { OwnerResponse(it) }

    @PutMapping("/api/v1/owners/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun putOwner(
        @PathVariable("id") id: Long,
        @RequestBody @Valid body: OwnerRequest.OwnerPutRequest
    ) = ownerService.putOwner(id, body.firstName, body.lastName, body.address, body.city, body.telephone)?.let { OwnerResponse(it) } ?: throw OwnerNotFoundException()

    @DeleteMapping("/api/v1/owners/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteOwner(@PathVariable id: Long) = try {
        ownerService.deleteOwner(id)
    } catch (e: EmptyResultDataAccessException) {
        throw OwnerNotFoundException()
    }
}