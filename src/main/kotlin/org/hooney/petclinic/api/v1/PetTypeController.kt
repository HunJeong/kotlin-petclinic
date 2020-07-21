package org.hooney.petclinic.api.v1

import org.hooney.petclinic.api.v1.response.PetTypeResponse
import org.hooney.petclinic.service.PetTypeService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PetTypeController(
    val petTypeService: PetTypeService
) {

    @GetMapping("/api/v1/pet_types", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getPetTypes() = petTypeService.findAll().map { PetTypeResponse(it) }

}