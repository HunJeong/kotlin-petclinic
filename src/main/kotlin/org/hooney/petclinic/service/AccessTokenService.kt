package org.hooney.petclinic.service

import org.hooney.petclinic.entity.AccessToken
import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.exception.OwnerNotFoundException
import org.hooney.petclinic.repository.AccessTokenRepository
import org.springframework.stereotype.Service

@Service
class AccessTokenService(
    val accessTokenRepository: AccessTokenRepository
) {

    fun createAccessToken(owner: Owner): AccessToken {
        if (owner.isNew()) { throw OwnerNotFoundException() }
        return AccessToken(owner = owner).let { accessTokenRepository.save(it) }
    }

    fun findByToken(token: String): AccessToken? = accessTokenRepository.findByToken(token)

}