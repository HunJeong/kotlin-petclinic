package org.hooney.petclinic.api.v1

import org.hooney.petclinic.exceptions.OwnerNotFoundException
import org.hooney.petclinic.service.OwnerService
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import javax.validation.constraints.Size

@RestController
@Validated
class OwnerController(val ownerService: OwnerService) {

    @GetMapping("/api/v1/owners", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getOwners() = this.ownerService.getOwners()

    @GetMapping("/api/v1/owners/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getOwner(@PathVariable("id") id: Long) = this.ownerService.getOwner(id) ?: throw OwnerNotFoundException()

    @PostMapping("/api/v1/owner", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun createOwner(
        @RequestParam firstName: String,
        @RequestParam lastName: String,
        @RequestParam address: String,
        @RequestParam(required = false) city: String?,
        @RequestParam @Size(min = 9, max = 13) telephone: String
    ) = ownerService.createOwner(firstName, lastName, address, city, telephone)

    @PutMapping("/api/v1/owners/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun putOwner(
        @PathVariable("id") id: Long,
        @RequestParam(required = false) firstName: String?,
        @RequestParam(required = false) lastName: String?,
        @RequestParam(required = false) address: String?,
        @RequestParam(required = false) city: String?,
        @RequestParam(required = false) @Size(min = 9, max = 13) telephone: String?
    ) = ownerService.putOwner(id, firstName, lastName, address, city, telephone) ?: throw OwnerNotFoundException()

    @DeleteMapping("/api/v1/owners/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteOwner(@PathVariable id: Long) = try {
        ownerService.deleteOwner(id)
    } catch (e: EmptyResultDataAccessException) {
        throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }
}