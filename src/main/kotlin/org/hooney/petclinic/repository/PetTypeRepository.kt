package org.hooney.petclinic.repository

import org.hooney.petclinic.entity.PetType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PetTypeRepository: JpaRepository<PetType, Long> {

    @Query(value = "SELECT * FROM types WHERE name = ?1 LIMIT 1", nativeQuery = true)
    fun findByNameOne(name: String): PetType?

}