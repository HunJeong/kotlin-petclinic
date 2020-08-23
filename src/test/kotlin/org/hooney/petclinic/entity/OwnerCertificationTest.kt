package org.hooney.petclinic.entity

import com.github.javafaker.Faker
import org.hooney.petclinic.util.sha256
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("OwnerCertification")
class OwnerCertificationTest {

    @Nested
    @DisplayName("setDigestedPassword")
    inner class SetDigestedPassword {

        @DisplayName("암호화된다.")
        @Test
        fun setDigestedPassword() {
            val owner = Owner()
            val email = Faker().internet().emailAddress()
            val password = Faker().internet().password()

            val ownerCertification = OwnerCertification(owner, email, password)
            assertEquals(ownerCertification.digestedPassword, password.sha256())
        }

    }

}