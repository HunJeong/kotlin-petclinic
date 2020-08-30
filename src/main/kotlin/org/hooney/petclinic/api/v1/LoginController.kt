package org.hooney.petclinic.api.v1


import org.hooney.petclinic.api.v1.request.SigninRequest
import org.hooney.petclinic.api.v1.request.SignupRequest
import org.hooney.petclinic.api.v1.response.AccessTokenResponse
import org.hooney.petclinic.service.AccessTokenService
import org.hooney.petclinic.service.OwnerCertificationService
import org.hooney.petclinic.service.OwnerService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class LoginController(
    val ownerService: OwnerService,
    val ownerCertificationService: OwnerCertificationService,
    val accessTokenService: AccessTokenService
) {

    @PostMapping("/api/v1/signup", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    fun signup(@RequestBody @Valid body: SignupRequest): AccessTokenResponse {
        val owner = ownerService.createOwner(body.firstName, body.lastName, body.address, body.city, body.telephone)
        ownerCertificationService.createOwnerCertification(owner, body.email, body.password)
        val accessToken = accessTokenService.createAccessToken(owner)
        return AccessTokenResponse(accessToken)
    }

    @PostMapping("/api/v1/signin", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun signin(@RequestBody @Valid body: SigninRequest): AccessTokenResponse {
        val owner = ownerCertificationService.getOwnerCertification(body.email, body.password).owner
        val accessToken = accessTokenService.createAccessToken(owner)
        return AccessTokenResponse(accessToken)
    }

}