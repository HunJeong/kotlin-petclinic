package org.hooney.petclinic.repository

import org.hooney.petclinic.entity.Pet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PetRepository: JpaRepository<Pet, Long> {

    @Query(value = "SELECT * FROM pets where owner_id = ?1", nativeQuery = true)
    fun findAllByOwnerIdWithoutOwner(ownerId: Long): List<Pet>

}