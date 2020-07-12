package org.hooney.petclinic.api.v1.response

import org.hooney.petclinic.entity.Speciality

class SpecialityResponse(speciality: Speciality) {
    val name: String? = speciality.name
}