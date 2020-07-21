package org.hooney.petclinic.service

import org.hooney.petclinic.api.v1.response.VetResponse
import org.hooney.petclinic.repository.VetRepository
import org.springframework.stereotype.Service

@Service
class VetService(
        val vetRepository: VetRepository
) {
    fun getAllVets() = vetRepository.findAll()
}