package org.hooney.petclinic.service

import org.hooney.petclinic.entity.Visit
import org.hooney.petclinic.exceptions.PetNotFoundException
import org.hooney.petclinic.repository.OwnerRepository
import org.hooney.petclinic.repository.PetRepository
import org.hooney.petclinic.repository.VisitRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class VisitService(
    val ownerRepository: OwnerRepository,
    val petRepository: PetRepository,
    val visitRepository: VisitRepository
) {

    fun getVisits(ownerId: Long, petId: Long): List<Visit> {
        val id = petRepository.findByIdAndOwnerId(petId, ownerId)?.id ?: throw PetNotFoundException()
        return visitRepository.findAllByPetId(id)
    }

    fun createVisit(ownerId: Long, petId: Long, description: String, date: LocalDate): Visit {
        return visitRepository.save(Visit(petId = petId, description = description, date = date))
    }

}