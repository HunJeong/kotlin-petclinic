package org.hooney.petclinic.api.v1

import org.hooney.petclinic.service.PetService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class PetController(
    val petService: PetService
) {

    @GetMapping("/api/v1/owners/{owner_id}/pets", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getPets(@PathVariable("owner_id") ownerId: Long) = petService.getOwnerPets(ownerId)

}