package org.hooney.petclinic.api.v1

import com.github.javafaker.Faker
import org.hooney.petclinic.constant.Profile
import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.entity.OwnerCertification
import org.hooney.petclinic.repository.AccessTokenRepository
import org.hooney.petclinic.repository.OwnerCertificationRepository
import org.hooney.petclinic.repository.OwnerRepository
import org.hooney.petclinic.test_util.HttpBodyBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@ActiveProfiles(Profile.TEST)
@DisplayName("LoginController")
class LoginControllerIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var ownerRepository: OwnerRepository

    @Autowired
    lateinit var ownerCertificationRepository: OwnerCertificationRepository

    @Autowired
    lateinit var accessTokenRepository: AccessTokenRepository

    @Nested
    @DisplayName("POST /api/v1/signup")
    inner class Signup {
        val firstName = Faker().pokemon().name()
        val lastName = Faker().pokemon().name()
        val address = Faker().address().fullAddress()
        val telephone = Faker().number().digits(10)

        val email = Faker().internet().emailAddress()
        val password = Faker().internet().password(10, 16)

        @Test
        @DisplayName("성공")
        fun signup() {
            assertEquals(ownerRepository.count(), 0)
            assertEquals(accessTokenRepository.count(), 0)
            val action = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        HttpBodyBuilder(
                            "firstName" to firstName,
                            "lastName" to lastName,
                            "address" to address,
                            "telephone" to telephone,
                            "email" to email,
                            "password" to password
                        ).build()
                    )
            )
            action.andExpect(MockMvcResultMatchers.status().isCreated)
            assertEquals(ownerRepository.count(), 1)
            assertEquals(accessTokenRepository.count(), 1)
        }
    }

    @Nested
    @DisplayName("POST /api/v1/signin")
    inner class Signin {
        val firstName = Faker().pokemon().name()
        val lastName = Faker().pokemon().name()
        val address = Faker().address().fullAddress()
        val telephone = Faker().number().digits(10)

        val email = Faker().internet().emailAddress()
        val password = Faker().internet().password(10, 16)

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

        @Test
        @DisplayName("성공")
        fun signin() {
            val wasAccessTokenCount = accessTokenRepository.count()
            val action = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/signin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        HttpBodyBuilder(
                            "email" to email,
                            "password" to password
                        ).build()
                    )
            )
            val changedAccessTokenCount = accessTokenRepository.count() - wasAccessTokenCount
            action.andExpect(status().isOk)
            assertEquals(changedAccessTokenCount, 1)
        }

        @Test
        @DisplayName("잘못된 이메일")
        fun signingNoOwner() {
            val _email = Faker().internet().emailAddress()
            val wasAccessTokenCount = accessTokenRepository.count()
            val action = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/signin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        HttpBodyBuilder(
                            "email" to _email,
                            "password" to password
                        ).build()
                    )
            )
            val changedAccessTokenCount = accessTokenRepository.count() - wasAccessTokenCount
            action.andExpect(status().isNotFound)
            assertEquals(changedAccessTokenCount, 0)
        }

        @Test
        @DisplayName("잘못된 비밀번호")
        fun signinInvalidPassword() {
            val _password = Faker().internet().password(10, 16)
            val wasAccessTokenCount = accessTokenRepository.count()
            val action = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/signin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        HttpBodyBuilder(
                            "email" to email,
                            "password" to _password
                        ).build()
                    )
            )
            val changedAccessTokenCount = accessTokenRepository.count() - wasAccessTokenCount
            action.andExpect(status().isBadRequest)
            assertEquals(changedAccessTokenCount, 0)
        }
    }

}