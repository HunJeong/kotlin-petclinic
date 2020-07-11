package org.hooney.petclinic.api.v1

import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.repository.OwnerRepository
import org.hooney.petclinic.service.OwnerService
import org.hooney.petclinic.utils.unwrap
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
class OwnerController(val ownerService: OwnerService) {

    @GetMapping("/api/v1/owners", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getOwners() = this.ownerService.getOwners()

    @GetMapping("/api/v1/owners/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getOwner(@PathVariable("id") id: Long) = this.ownerService.getOwner(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @PostMapping("/api/v1/owner", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun createOwner(
        @RequestParam firstName: String,
        @RequestParam lastName: String,
        @RequestParam address: String,
        @RequestParam(required = false) city: String?,
        @RequestParam telephone: String
    ) = ownerService.createOwner(firstName, lastName, address, city, telephone)

    @PutMapping("/api/v1/owners/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun putOwner(
        @PathVariable("id") id: Long,
        @RequestParam(required = false) firstName: String?,
        @RequestParam(required = false) lastName: String?,
        @RequestParam(required = false) address: String?,
        @RequestParam(required = false) city: String?,
        @RequestParam(required = false) telephone: String?
    ) = ownerService.putOwner(id, firstName, lastName, address, city, telephone) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @DeleteMapping("/api/v1/owners/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteOwner(@PathVariable id: Long) = try {
        ownerService.deleteOwner(id)
    } catch (e: EmptyResultDataAccessException) {
        throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }
}