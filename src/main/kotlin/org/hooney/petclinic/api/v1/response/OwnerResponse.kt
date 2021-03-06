package org.hooney.petclinic.api.v1.response

import org.hooney.petclinic.annotation.NoArgConstructor
import org.hooney.petclinic.entity.Owner

@NoArgConstructor
class OwnerResponse(owner: Owner) {
    val firstName = owner.firstName
    val lastName = owner.lastName
    val address = owner.address
    val city = owner.city
    val telephone = owner.telephone
}