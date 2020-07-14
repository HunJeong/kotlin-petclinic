package org.hooney.petclinic.exceptions

sealed class NotFoundException(message: String?) : PetClinicException(message)
class OwnerNotFoundException(message: String="주인을 찾을 수 없습니다."): NotFoundException(message)

