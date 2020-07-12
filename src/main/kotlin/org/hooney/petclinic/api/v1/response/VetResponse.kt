package org.hooney.petclinic.api.v1.response

import org.hooney.petclinic.entity.Vet

class VetResponse(vet: Vet) {
    val firstName = vet.firstName
    val lastName = vet.lastName
    val specialities = vet.getSpecialities().map { SpecialityResponse(it) }
}