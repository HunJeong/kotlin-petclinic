package org.hooney.petclinic.service

import org.hooney.petclinic.api.v1.response.PetTypeResponse
import org.hooney.petclinic.entity.PetType
import org.hooney.petclinic.repository.PetTypeRepository
import org.springframework.stereotype.Service

@Service
class PetTypeService(
    val petTypeRepository: PetTypeRepository
) {

    fun findAll(): List<PetTypeResponse> = petTypeRepository.findAll().map { PetTypeResponse(it) }

}