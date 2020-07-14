package org.hooney.petclinic.api.v1.response

import org.hooney.petclinic.entity.Pet

class PetResponse(pet: Pet) {
    val name = pet.name
    val birthday = pet.birthDate
}