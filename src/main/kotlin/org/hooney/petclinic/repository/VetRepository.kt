package org.hooney.petclinic.repository

import org.hooney.petclinic.entity.Vet
import org.springframework.data.jpa.repository.JpaRepository

interface VetRepository: JpaRepository<Vet, Long> {
}