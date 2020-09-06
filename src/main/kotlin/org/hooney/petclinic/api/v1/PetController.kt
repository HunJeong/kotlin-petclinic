package org.hooney.petclinic.api.v1

import org.hooney.petclinic.annotation.Authorized
import org.hooney.petclinic.api.v1.request.PetCreateRequest
import org.hooney.petclinic.api.v1.response.PetResponse
import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.service.PetService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class PetController(
    val petService: PetService
) {

    @GetMapping("/api/v1/owners/{owner_id}/pets", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getPets(@PathVariable("owner_id") ownerId: Long) = petService.getOwnerPets(ownerId).map { PetResponse(it) }

    @Authorized
    @GetMapping("/api/v1/pets", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getPets2(@RequestAttribute("owner") owner: Owner) = petService.getOwnerPets(owner.id!!).map { PetResponse(it) }

    @PostMapping("/api/v1/owners/{owner_id}/pets", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun postPets(
        @PathVariable("owner_id") ownerId: Long,
        @RequestBody @Valid body: PetCreateRequest
    ) = PetResponse(petService.createOwnerPets(ownerId, body.name, body.birthDate!!, body.type))

}