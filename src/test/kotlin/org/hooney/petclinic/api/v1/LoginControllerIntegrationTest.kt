package org.hooney.petclinic.api.v1

import com.github.javafaker.Faker
import org.hooney.petclinic.api.v1.request.SignupRequest
import org.hooney.petclinic.api.v1.response.AccessTokenResponse
import org.hooney.petclinic.api.v1.response.MessageResponse
import org.hooney.petclinic.entity.AccessToken
import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.entity.OwnerCertification
import org.hooney.petclinic.repository.AccessTokenRepository
import org.hooney.petclinic.repository.OwnerCertificationRepository
import org.hooney.petclinic.repository.OwnerRepository
import org.hooney.petclinic.test_util.HttpBodyBuilder
import org.hooney.petclinic.test_util.annotation.IntegrationTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.util.MultiValueMap

@IntegrationTest
@DisplayName("LoginController")
class LoginControllerIntegrationTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Autowired
    lateinit var ownerRepository: OwnerRepository

    @Autowired
    lateinit var ownerCertificationRepository: OwnerCertificationRepository

    @Autowired
    lateinit var accessTokenRepository: AccessTokenRepository

    @Nested
    @DisplayName("POST /api/v1/signup")
    inner class Signup {
        var firstName = Faker().pokemon().name()
        var lastName = Faker().pokemon().name()
        var address = Faker().address().fullAddress()
        var telephone = Faker().number().digits(10)

        var email = Faker().internet().emailAddress()
        var password = Faker().internet().password(10, 16)

        var params = mutableMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "address" to address,
            "telephone" to telephone,
            "email" to email,
            "password" to password
        )

        inline fun <reified T> subject(): ResponseEntity<T> {
            return testRestTemplate.exchange(
                "/api/v1/signup",
                HttpMethod.POST,
                HttpEntity(
                    HttpBodyBuilder(params).build(),
                    HttpHeaders().apply { this.contentType = MediaType.APPLICATION_JSON }
                ),
                T::class.java
            )
        }

        @Test
        @DisplayName("성공")
        fun signup() {
            assertEquals(ownerRepository.count(), 0)
            assertEquals(accessTokenRepository.count(), 0)

            val response = subject<AccessTokenResponse>()

            assertEquals(response.statusCode, HttpStatus.CREATED)
            assertEquals(ownerRepository.count(), 1)
            assertEquals(accessTokenRepository.count(), 1)
        }
    }

    @Nested
    @DisplayName("POST /api/v1/signin")
    inner class Signin {
        var firstName = Faker().pokemon().name()
        var lastName = Faker().pokemon().name()
        var address = Faker().address().fullAddress()
        var telephone = Faker().number().digits(10)

        var email = Faker().internet().emailAddress()
        var password = Faker().internet().password(10, 16)

        var params = mutableMapOf("email" to email, "password" to password)

        @BeforeEach
        fun beforeEach() {
            val owner = Owner(
                firstName = firstName,
                lastName = lastName,
                telephone = telephone,
                address = address
            )
            ownerRepository.save(owner)

            val ownerCertification = OwnerCertification(
                owner = owner,
                email = email,
                password = password
            )
            ownerCertificationRepository.save(ownerCertification)
        }

        inline fun <reified T> subject(): ResponseEntity<T> {
            return testRestTemplate.exchange(
                "/api/v1/signin",
                HttpMethod.POST,
                HttpEntity(
                    HttpBodyBuilder(params).build(),
                    HttpHeaders().apply { this.contentType = MediaType.APPLICATION_JSON }
                ),
                T::class.java
            )
        }

        @Test
        @DisplayName("성공")
        fun signin() {
            val wasAccessTokenCount = accessTokenRepository.count()

            val response = subject<AccessTokenResponse>()

            val changedAccessTokenCount = accessTokenRepository.count() - wasAccessTokenCount
            assertEquals(response.statusCode, HttpStatus.OK)
            assertEquals(changedAccessTokenCount, 1)
        }

        @Test
        @DisplayName("잘못된 이메일")
        fun signingNoOwner() {
            params["email"] = Faker().internet().emailAddress()
            val wasAccessTokenCount = accessTokenRepository.count()

            val response = subject<MessageResponse>()

            val changedAccessTokenCount = accessTokenRepository.count() - wasAccessTokenCount
            assertEquals(response.statusCode, HttpStatus.NOT_FOUND)
            assertEquals(response.body.success, false)
            assertEquals(changedAccessTokenCount, 0)
        }

        @Test
        @DisplayName("잘못된 비밀번호")
        fun signinInvalidPassword() {
            params["password"] = Faker().internet().password(10, 16)

            val wasAccessTokenCount = accessTokenRepository.count()

            val response = subject<MessageResponse>()

            val changedAccessTokenCount = accessTokenRepository.count() - wasAccessTokenCount
            assertEquals(response.statusCode, HttpStatus.BAD_REQUEST)
            assertEquals(response.body.success, false)
            assertEquals(changedAccessTokenCount, 0)
        }
    }

}