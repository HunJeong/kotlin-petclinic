package org.hooney.petclinic.api.v1.response

import org.hooney.petclinic.entity.Vet

class VetResponse(vet: Vet) {
    val firstName: String? = vet.firstName
    val lastName: String? = vet.lastName
}