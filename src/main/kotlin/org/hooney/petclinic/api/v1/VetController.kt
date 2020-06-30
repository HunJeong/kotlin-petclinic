package org.hooney.petclinic.api.v1

import org.hooney.petclinic.entity.Vet
import org.hooney.petclinic.repository.VetRepository
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class VetController(val vetRepository: VetRepository) {

    @GetMapping("/api/v1/vets", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getVets(): List<Vet> = this.vetRepository.findAll()

}