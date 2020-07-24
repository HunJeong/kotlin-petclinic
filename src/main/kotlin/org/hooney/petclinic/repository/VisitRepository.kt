package org.hooney.petclinic.repository

import org.hooney.petclinic.entity.Visit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface VisitRepository: JpaRepository<Visit, Long> {

    @Query(value = "SELECT * FROM visits WHERE pet_id = :petId", nativeQuery = true)
    @Transactional(readOnly = true)
    fun findAllByPetId(@Param("petId") petId: Long): List<Visit>

}