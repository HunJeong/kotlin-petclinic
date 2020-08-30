package org.hooney.petclinic.api.v1

import com.github.javafaker.Faker
import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.entity.OwnerCertification
import org.hooney.petclinic.exception.OwnerNotFoundException
import org.hooney.petclinic.exception.WrongPasswordException
import org.hooney.petclinic.repository.OwnerCertificationRepository
import org.hooney.petclinic.repository.OwnerRepository
import org.hooney.petclinic.test_util.HttpBodyBuilder
import org.hooney.petclinic.test_util.annotation.IntegrationTest
import org.hooney.petclinic.util.unwrap
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@IntegrationTest
@DisplayName("OwnerController")
class OwnerControllerIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var ownerRepository: OwnerRepository

    @Autowired
    lateinit var ownerCertificationRepository: OwnerCertificationRepository

    @Nested
    @DisplayName("PUT /api/v1/owners/{ownerId}")
    inner class PutOwner {
        val wasFirstName = Faker().pokemon().name()
        val wasLastName = Faker().pokemon().name()
        val wasAddress = Faker().address().fullAddress()
        val wasTelephone = Faker().number().digits(10)

        val firstName = Faker().pokemon().name()
        val lastName = Faker().pokemon().name()
        val address = Faker().address().fullAddress()
        val telephone = Faker().number().digits(10)

        @Test
        @DisplayName("성공")
        fun putOwner() {
            val owner = Owner(
                    firstName = wasFirstName,
                    lastName = wasLastName,
                    address = wasAddress,
                    telephone = wasTelephone
            )
            ownerRepository.save(owner)

            val action = mockMvc.perform(put("/api/v1/owners/${owner.id!!}")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(HttpBodyBuilder(
                            "firstName" to firstName,
                            "lastName" to lastName,
                            "address" to address,
                            "telephone" to telephone
                    ).build())
            )

            action.andExpect(status().isOk).andDo(print())

            val updated_owner = ownerRepository.findById(owner.id!!).unwrap()
            assertEquals(updated_owner?.firstName, firstName)
            assertEquals(updated_owner?.lastName, lastName)
            assertEquals(updated_owner?.address, address)
            assertEquals(updated_owner?.telephone, telephone)
        }

        @Test
        @DisplayName("owner가 없음")
        fun putOwnerOwnerNotFound() {
            val action = mockMvc.perform(put("/api/v1/owners/999").contentType(MediaType.APPLICATION_JSON)
                    .content(HttpBodyBuilder(
                            "firstName" to firstName,
                            "lastName" to lastName,
                            "address" to address,
                            "telephone" to telephone
                    ).build())
            )
            action.andExpect(status().isNotFound)
        }
    }

    @Nested
    @DisplayName("DELETE /api/1/owners/{ownerId}")
    inner class DeleteOwner {
        val firstName = Faker().pokemon().name()
        val lastName = Faker().pokemon().name()
        val address = Faker().address().fullAddress()
        val telephone = Faker().number().digits(10)

        @Test
        @DisplayName("성공")
        fun deleteOwner() {
            val owner = Owner(
                    firstName = firstName,
                    lastName = lastName,
                    address = address,
                    telephone = telephone
            )
            ownerRepository.save(owner)

            mockMvc.perform(delete("/api/v1/owners/${owner.id!!}"))
                    .andExpect(status().isNoContent)
            assertNull(ownerRepository.findById(owner.id!!).unwrap())
        }

        @Test
        @DisplayName("사용자 없음")
        fun deleteOwnerNoData() {
            val id = Faker().number().randomNumber()
            mockMvc.perform(delete("/api/v1/owners/${id}"))
                    .andExpect(status().isNotFound)
        }
    }

    @Nested
    @DisplayName("POST /api/v1/owners/signup")
    inner class SignupOwner() {
        val email = Faker().internet().emailAddress()
        val password = Faker().internet().password(10, 16)
        val firstName = Faker().pokemon().name()
        val lastName = Faker().pokemon().name()
        val address = Faker().address().fullAddress()
        val telephone = Faker().number().digits(10)

        @Test
        @DisplayName("성공")
        fun signupOwner() {
            val wasOwnerCount = ownerRepository.count()
            val wasOwnerCertificationCount = ownerCertificationRepository.count()

            val action = mockMvc.perform(post("/api/v1/owners/signup").contentType(MediaType.APPLICATION_JSON)
                .content(HttpBodyBuilder(
                    "email" to email,
                    "password" to password,
                    "firstName" to firstName,
                    "lastName" to lastName,
                    "address" to address,
                    "telephone" to telephone
                ).build())
            )

            action.andExpect(status().isCreated)
            assertEquals(ownerRepository.count(), wasOwnerCount + 1)
            assertEquals(ownerCertificationRepository.count(), wasOwnerCertificationCount + 1)
        }

        @Test
        @DisplayName("중복 이메일일 때")
        fun signupOwnerExistedOwner() {
            val owner = Owner(firstName, lastName, address, telephone)
            ownerRepository.save(owner)
            val ownerCertification = OwnerCertification(owner, email, password)
            ownerCertificationRepository.save(ownerCertification)

            val wasOwnerCount = ownerRepository.count()
            val wasOwnerCertificationCount = ownerCertificationRepository.count()

            val action = mockMvc.perform(post("/api/v1/owners/signup").contentType(MediaType.APPLICATION_JSON)
                .content(HttpBodyBuilder(
                    "email" to email,
                    "password" to password,
                    "firstName" to firstName,
                    "lastName" to lastName,
                    "address" to address,
                    "telephone" to telephone
                ).build())
            )

            action.andExpect(status().isBadRequest)
            assertEquals(ownerRepository.count(), wasOwnerCount)
            assertEquals(ownerCertificationRepository.count(), wasOwnerCertificationCount)
        }
    }

    @Nested
    @DisplayName("POST /api/v1/owners/signin")
    inner class SigninOwner() {
        val email = Faker().internet().emailAddress()
        val password = Faker().internet().password(10, 16)
        val firstName = Faker().pokemon().name()
        val lastName = Faker().pokemon().name()
        val address = Faker().address().fullAddress()
        val telephone = Faker().number().digits(10)

        @Test
        @DisplayName("성공")
        fun signinOwner() {
            val owner = Owner(firstName, lastName, address, telephone)
            ownerRepository.save(owner)
            val ownerCertification = OwnerCertification(owner, email, password)
            ownerCertificationRepository.save(ownerCertification)

            val action = mockMvc.perform(post("/api/v1/owners/signin").contentType(MediaType.APPLICATION_JSON)
                .content(HttpBodyBuilder(
                    "email" to email,
                    "password" to password
                ).build())
            )

            action.andExpect(status().isOk)
        }

        @Test
        @DisplayName("비밀번호 틀림")
        fun signinOwnerWrongPassword() {
            val owner = Owner(firstName, lastName, address, telephone)
            ownerRepository.save(owner)
            val ownerCertification = OwnerCertification(owner, email, password)
            ownerCertificationRepository.save(ownerCertification)

            val _password = Faker().internet().password(10, 16)

            val action = mockMvc.perform(post("/api/v1/owners/signin").contentType(MediaType.APPLICATION_JSON)
                .content(HttpBodyBuilder(
                    "email" to email,
                    "password" to _password
                ).build())
            )

            action.andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message").value(WrongPasswordException().message!!))
        }

        @Test
        @DisplayName("없는 계정")
        fun signinOwnerNoOwner() {
            val owner = Owner(firstName, lastName, address, telephone)
            ownerRepository.save(owner)
            val ownerCertification = OwnerCertification(owner, email, password)
            ownerCertificationRepository.save(ownerCertification)

            val _email = Faker().internet().emailAddress()

            val action = mockMvc.perform(post("/api/v1/owners/signin").contentType(MediaType.APPLICATION_JSON)
                .content(HttpBodyBuilder(
                    "email" to _email,
                    "password" to password
                ).build())
            )

            action.andExpect(status().isNotFound)
                .andExpect(jsonPath("$.message").value(OwnerNotFoundException().message!!))
        }
    }

}