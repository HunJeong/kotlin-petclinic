package org.hooney.petclinic.service

import org.hooney.petclinic.api.v1.response.PetResponse
import org.hooney.petclinic.exceptions.OwnerNotFoundException
import org.hooney.petclinic.repository.OwnerRepository
import org.hooney.petclinic.repository.PetRepository
import org.springframework.stereotype.Service

@Service
class PetService(
    val ownerRepository: OwnerRepository,
    val petRepository: PetRepository
) {

    @Throws(OwnerNotFoundException::class)
    fun getOwnerPets(ownerId: Long) = petRepository.findAllByOwnerId(ownerId).map { PetResponse(it) }

}