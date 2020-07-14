package org.hooney.petclinic.repository

import org.hooney.petclinic.entity.Visit
import org.springframework.data.jpa.repository.JpaRepository

interface VisitRepository: JpaRepository<Visit, Long> {
}