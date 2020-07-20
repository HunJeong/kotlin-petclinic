package org.hooney.petclinic.api.v1.response

import org.hooney.petclinic.entity.Pet
import java.time.format.DateTimeFormatter

class PetResponse(pet: Pet) {
    val name = pet.name
    val birthDate = pet.birthDate?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}