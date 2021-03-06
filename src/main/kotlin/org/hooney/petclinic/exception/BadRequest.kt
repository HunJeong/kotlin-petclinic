package org.hooney.petclinic.exception

import org.springframework.http.HttpStatus

sealed class BadRequest(message: String="Bad Request"): PetClinicException(message, HttpStatus.BAD_REQUEST)
class ExistedEmailException(message: String="이미 가입된 계정입니다."): BadRequest(message)
class WrongPasswordException(message: String="비밀번호가 틀렸습니다."): BadRequest(message)