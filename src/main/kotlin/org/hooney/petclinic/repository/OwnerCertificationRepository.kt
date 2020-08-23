package org.hooney.petclinic.repository

import org.hooney.petclinic.entity.OwnerCertification
import org.springframework.data.jpa.repository.JpaRepository

interface OwnerCertificationRepository: JpaRepository<OwnerCertification, Long> {

    fun existsByEmail(email: String): Boolean

}