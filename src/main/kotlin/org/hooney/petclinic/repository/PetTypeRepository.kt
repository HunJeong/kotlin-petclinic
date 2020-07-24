package org.hooney.petclinic.repository

import org.hooney.petclinic.entity.PetType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface PetTypeRepository: JpaRepository<PetType, Long> {

    @Query(value = "SELECT * FROM types WHERE name = :name LIMIT 1", nativeQuery = true)
    @Transactional(readOnly = true)
    fun findByName(@Param("name") name: String): PetType?

}