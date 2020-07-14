package org.hooney.petclinic.repository

import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.entity.Pet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PetRepository: JpaRepository<Pet, Long> {

    fun findAllByOwnerId(ownerId: Long): List<Pet>

}