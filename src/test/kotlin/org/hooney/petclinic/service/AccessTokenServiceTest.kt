package org.hooney.petclinic.service

import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.exception.OwnerNotFoundException
import org.hooney.petclinic.repository.AccessTokenRepository
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest(classes = [AccessTokenService::class])
@DisplayName("AccessTokenService")
class AccessTokenServiceTest {

    @MockBean
    lateinit var accessTokenRepository: AccessTokenRepository

    @Autowired
    lateinit var accessTokenService: AccessTokenService

    @Nested
    @DisplayName("#createAccessToken")
    inner class CreateAccessToken {

        val owner = Owner()

        @Test
        @DisplayName("owner가 db에 없음")
        fun createAccessTokenNoOwner() {
            assertThrows(OwnerNotFoundException::class.java) { accessTokenService.createAccessToken(owner) }
        }

    }

}