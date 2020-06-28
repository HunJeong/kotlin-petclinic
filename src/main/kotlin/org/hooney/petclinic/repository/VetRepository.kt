package org.hooney.petclinic.repository

import org.hooney.petclinic.entity.Vet
import org.springframework.data.repository.Repository
import org.springframework.transaction.annotation.Transactional

interface VetRepository: Repository<Vet, Long> {

    @Transactional(readOnly = true)
    fun findAll(): List<Vet>

}