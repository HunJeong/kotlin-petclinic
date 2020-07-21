package org.hooney.petclinic.repository

import org.hooney.petclinic.entity.Visit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface VisitRepository: JpaRepository<Visit, Long> {

    @Query(value = "SELECT * FROM visits WHERE pet_id = ?1", nativeQuery = true)
    fun findAllByPetId(petId: Long): List<Visit>

}