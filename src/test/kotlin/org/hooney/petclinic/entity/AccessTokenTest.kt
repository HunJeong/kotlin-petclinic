package org.hooney.petclinic.entity

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

@DisplayName("AccessToken")
class AccessTokenTest {

    @Nested
    @DisplayName("#isExpire")
    inner class IsExpire {

        val owner = Owner()

        @Test
        @DisplayName("만료 안 됨")
        fun isExpireNotExpired() {
            val accessToken = AccessToken(owner = owner)
            assertFalse(accessToken.isExpire())
        }

        @Test
        @DisplayName("만료 됨")
        fun isExpireExpired() {
            val accessToken = AccessToken(owner = owner, expireAt = LocalDateTime.now().minusHours(1))
            assertTrue(accessToken.isExpire())
        }

        @Test
        @DisplayName("expireAt == null")
        fun isExpireExpireAtIsNull() {
            val accessToken = AccessToken(owner = owner, expireAt = null)
            assertFalse(accessToken.isExpire())
        }
    }

}