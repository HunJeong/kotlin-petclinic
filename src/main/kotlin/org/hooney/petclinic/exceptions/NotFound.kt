package org.hooney.petclinic.exceptions

import org.springframework.http.HttpStatus

sealed class NotFoundException(message: String="Not Found"): PetClinicException(message, HttpStatus.NOT_FOUND)
class OwnerNotFoundException(message: String="주인을 찾을 수 없습니다."): NotFoundException(message)
class PetTypeNotFoundException(message: String="애완동물 종류를 찾을 수 없습니다."): NotFoundException(message)