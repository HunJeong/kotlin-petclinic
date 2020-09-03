package org.hooney.petclinic.api.v1.response

import org.hooney.petclinic.annotation.NoArgConstructor
import org.hooney.petclinic.entity.PetType

@NoArgConstructor
class PetTypeResponse(petType: PetType) {
    val name = petType.name
}