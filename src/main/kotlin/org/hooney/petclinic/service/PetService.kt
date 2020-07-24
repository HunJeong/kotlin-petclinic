package org.hooney.petclinic.service

import org.hooney.petclinic.api.v1.response.PetResponse
import org.hooney.petclinic.entity.Pet
import org.hooney.petclinic.exceptions.OwnerNotFoundException
import org.hooney.petclinic.exceptions.PetTypeNotFoundException
import org.hooney.petclinic.repository.OwnerRepository
import org.hooney.petclinic.repository.PetRepository
import org.hooney.petclinic.repository.PetTypeRepository
import org.hooney.petclinic.utils.unwrap
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class PetService(
    val petRepository: PetRepository,
    val ownerRepository: OwnerRepository,
    val petTypeRepository: PetTypeRepository
) {

    fun getOwnerPets(ownerId: Long) = petRepository.findAllByOwnerIdWithoutOwner(ownerId)

    fun createOwnerPets(ownerId: Long, name: String, birthDate: LocalDate, type: String): Pet {
        return Pet(
            name = name,
            birthDate = birthDate,
            owner = ownerRepository.findById(ownerId).unwrap() ?: throw OwnerNotFoundException(),
            type = petTypeRepository.findByName(type) ?: throw PetTypeNotFoundException()
        ).apply { petRepository.save(this) }
    }

}