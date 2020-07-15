package org.hooney.petclinic.service

import org.hooney.petclinic.api.v1.response.PetResponse
import org.hooney.petclinic.repository.PetRepository
import org.springframework.stereotype.Service

@Service
class PetService(
    val petRepository: PetRepository
) {

    fun getOwnerPets(ownerId: Long) = petRepository.findAllByOwnerIdWithoutOwner(ownerId).map { PetResponse(it) }

}