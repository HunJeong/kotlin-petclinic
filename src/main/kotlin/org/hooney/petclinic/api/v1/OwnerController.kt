package org.hooney.petclinic.api.v1

import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.repository.OwnerRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import javax.validation.constraints.Digits
import javax.validation.constraints.Max

@RestController
class OwnerController(val ownerRepository: OwnerRepository) {

    @GetMapping("/api/v1/owners", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getOwners(): List<Owner> = this.ownerRepository.findAll()

    @GetMapping("/api/v1/owners/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getOwner(@PathVariable("id") id: Long) = this.ownerRepository.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @PostMapping("/api/v1/owner", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun createOwner(
        @RequestParam firstName: String,
        @RequestParam lastName: String,
        @RequestParam address: String,
        @RequestParam(required = false) city: String?,
        @RequestParam telephone: String
    ): Owner {
        return Owner(
            firstName = firstName,
            lastName = lastName,
            address = address,
            city = city,
            telephone = telephone
        ).apply { ownerRepository.save(this) }
    }

    @PutMapping("/api/v1/owners/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun putOwner(
        @PathVariable("id") id: Long,
        @RequestParam(required = false) firstName: String?,
        @RequestParam(required = false) lastName: String?,
        @RequestParam(required = false) address: String?,
        @RequestParam(required = false) city: String?,
        @RequestParam(required = false) telephone: String?
    ): Owner {
        return (this.ownerRepository.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND))
            .apply { firstName?.let { this.firstName = firstName } }
            .apply { lastName?.let { this.lastName = lastName } }
            .apply { address?.let { this.address = address } }
            .apply { city?.let { this.city = city } }
            .apply { telephone?.let { this.telephone = telephone } }
            .apply { ownerRepository.save(this) }
    }

    @DeleteMapping("/api/v1/owners/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteOwner(@PathVariable id: Long) = try {
        this.ownerRepository.deleteById(id)
    } catch (e: EmptyResultDataAccessException) {
        throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }
}