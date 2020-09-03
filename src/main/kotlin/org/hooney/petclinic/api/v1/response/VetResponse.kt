package org.hooney.petclinic.api.v1.response

import org.hooney.petclinic.annotation.NoArgConstructor
import org.hooney.petclinic.entity.Vet

@NoArgConstructor
class VetResponse(vet: Vet) {
    val firstName = vet.firstName
    val lastName = vet.lastName
    val specialities = vet.getSpecialities().map { SpecialityResponse(it) }
}