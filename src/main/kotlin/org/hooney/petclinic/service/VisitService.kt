package org.hooney.petclinic.service

import org.hooney.petclinic.entity.Visit
import org.hooney.petclinic.exceptions.PetNotFoundException
import org.hooney.petclinic.exceptions.VisitNotFoundException
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
        val _petId = petRepository.findByIdAndOwnerId(petId, ownerId)?.id ?: throw PetNotFoundException()
        return visitRepository.findAllByPetId(_petId)
    }

    fun createVisit(ownerId: Long, petId: Long, description: String, date: LocalDate): Visit {
        return visitRepository.save(Visit(petId = petId, description = description, date = date))
    }

    fun putVisit(ownerId: Long, petId: Long, visitId: Long, description: String?, date: LocalDate?): Visit {
        val _petId = petRepository.findByIdAndOwnerId(petId, ownerId)?.id ?: throw PetNotFoundException()
        val visit = visitRepository.findByIdAndPetId(visitId, _petId) ?: throw VisitNotFoundException()
        return visit.apply {
            description?.let { this.description = description }
            date?.let { this.date = date }
            visitRepository.save(this)
        }
    }
}