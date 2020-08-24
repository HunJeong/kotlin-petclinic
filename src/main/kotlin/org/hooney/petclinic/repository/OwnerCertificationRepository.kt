package org.hooney.petclinic.repository

import org.hooney.petclinic.entity.OwnerCertification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OwnerCertificationRepository: JpaRepository<OwnerCertification, Long> {

    fun existsByEmail(email: String): Boolean

    @Query(value = "SELECT * FROM owner_certifications where email = :email limit 1", nativeQuery = true)
    fun findByEmail(@Param("email") email: String): OwnerCertification?
}