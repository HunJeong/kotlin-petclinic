package org.hooney.petclinic.api.v1

import org.hooney.petclinic.api.v1.request.OwnerCreateRequest
import org.hooney.petclinic.api.v1.request.OwnerPutRequest
import org.hooney.petclinic.api.v1.request.OwnerSigninRequest
import org.hooney.petclinic.api.v1.request.OwnerSignupRequest
import org.hooney.petclinic.api.v1.response.OwnerResponse
import org.hooney.petclinic.exception.OwnerNotFoundException
import org.hooney.petclinic.service.OwnerCertificationService
import org.hooney.petclinic.service.OwnerService
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class OwnerController(
    val ownerService: OwnerService,
    val ownerCertificationService: OwnerCertificationService
) {

    @GetMapping("/api/v1/owners", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getOwners() = this.ownerService.getOwners().map { OwnerResponse(it) }

    @GetMapping("/api/v1/owners/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getOwner(@PathVariable("id") id: Long) = this.ownerService.getOwner(id)?.let { OwnerResponse(it) } ?: throw OwnerNotFoundException()

    @PostMapping("/api/v1/owners", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun createOwner(
        @RequestBody @Valid body: OwnerCreateRequest
    ) = OwnerResponse(ownerService.createOwner(body.firstName, body.lastName, body.address, body.city, body.telephone))

    @PutMapping("/api/v1/owners/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun putOwner(
        @PathVariable("id") id: Long,
        @RequestBody @Valid body: OwnerPutRequest
    ) = ownerService.putOwner(id, body.firstName, body.lastName, body.address, body.city, body.telephone)?.let { OwnerResponse(it) } ?: throw OwnerNotFoundException()

    @DeleteMapping("/api/v1/owners/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteOwner(@PathVariable id: Long) = try {
        ownerService.deleteOwner(id)
    } catch (e: EmptyResultDataAccessException) {
        throw OwnerNotFoundException()
    }

    @PostMapping("/api/v1/owners/signup", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    fun signupOwner(@RequestBody @Valid body: OwnerSignupRequest): OwnerResponse {
        val owner = ownerService.createOwner(body.firstName, body.lastName, body.address, body.city, body.telephone)
        ownerCertificationService.createOwnerCertification(owner, body.email, body.password)
        return OwnerResponse(owner)
    }

    @PostMapping("/api/v1/owners/signin", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun signinOwner(@RequestBody @Valid body: OwnerSigninRequest): OwnerResponse {
        return OwnerResponse(ownerCertificationService.getOwnerCertification(body.email, body.password).owner)
    }
}