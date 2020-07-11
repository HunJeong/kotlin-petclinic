package org.hooney.petclinic.api.v1

import org.hooney.petclinic.entity.Vet
import org.hooney.petclinic.repository.VetRepository
import org.hooney.petclinic.service.VetService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class VetController(val vetService: VetService) {

    @GetMapping("/api/v1/vets", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getVets() = this.vetService.getAllVets()

}