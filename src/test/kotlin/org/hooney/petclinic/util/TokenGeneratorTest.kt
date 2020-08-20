package org.hooney.petclinic.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("TokenGenerator")
class TokenGeneratorTest {

    @Nested
    @DisplayName(".generateToken")
    inner class GenerateToken {

        @Test
        @DisplayName("성공")
        fun generateToken() {
            assertNotNull(TokenGenerator.generateToken())
        }

        @Test
        @DisplayName("길이")
        fun generateTokenWithLength() {
            val length = 64
            print(TokenGenerator.generateToken(64))
            assertEquals(TokenGenerator.generateToken(length).length, length)
        }

    }

}