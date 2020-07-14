package org.hooney.petclinic.api.v1.response

import org.hooney.petclinic.entity.PetType

class PetTypeResponse(petType: PetType) {
    val name = petType.name
}