package org.hooney.petclinic.api.v1.response

import org.hooney.petclinic.annotation.NoArgConstructor
import org.hooney.petclinic.entity.AccessToken
import org.hooney.petclinic.util.iso8601

@NoArgConstructor
class AccessTokenResponse(accessToken: AccessToken) {
    val token = accessToken.token
    val expireAt = accessToken.expireAt?.iso8601()
}