package org.hooney.petclinic.api.v1

import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.repository.OwnerRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class OwnerController(val ownerRepository: OwnerRepository) {

    @GetMapping("/api/v1/owners", produces = ["application/json"])
    fun getOwners(): List<Owner> = this.ownerRepository.findAll()

    @PostMapping("/api/v1/owner", produces = ["applicaiton/json"])
    fun createOwner(@RequestBody owner: Owner): Owner = owner.apply { ownerRepository.save(this) }
}