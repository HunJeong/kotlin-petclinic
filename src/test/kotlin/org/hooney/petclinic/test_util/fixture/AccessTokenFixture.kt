package org.hooney.petclinic.test_util.fixture

import org.hooney.petclinic.entity.AccessToken
import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.util.TokenGenerator
import java.time.LocalDateTime

fun Fixture.accessToken(
    owner: Owner,
    token: String = TokenGenerator.generateToken(),
    expireAt: LocalDateTime = AccessToken.defaultExpireAt()
) = AccessToken(
    owner = owner,
    token = token,
    expireAt = expireAt
)