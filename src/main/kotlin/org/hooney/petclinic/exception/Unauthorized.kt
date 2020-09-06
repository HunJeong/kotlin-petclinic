package org.hooney.petclinic.exception

import org.springframework.http.HttpStatus

sealed class Unauthorized(message: String="Unauthorized"): PetClinicException(message, HttpStatus.UNAUTHORIZED)
class LoginFailException(message: String="로그인에 실패하였습니다."): Unauthorized(message)