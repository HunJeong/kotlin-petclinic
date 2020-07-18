package org.hooney.petclinic.api.v1

import org.hooney.petclinic.api.v1.response.PetResponse
import org.hooney.petclinic.service.PetService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@Validated
class PetController(
    val petService: PetService
) {

    @GetMapping("/api/v1/owners/{owner_id}/pets", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getPets(@PathVariable("owner_id") ownerId: Long) = petService.getOwnerPets(ownerId).map { PetResponse(it) }

    @PostMapping("/api/v1/owners/{owner_id}/pets", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun postPets(
        @PathVariable("owner_id") ownerId: Long,
        @RequestParam name: String,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") birthDate: LocalDate,
        @RequestParam type: String
    ) = PetResponse(petService.createOwnerPets(ownerId, name, birthDate, type))

}