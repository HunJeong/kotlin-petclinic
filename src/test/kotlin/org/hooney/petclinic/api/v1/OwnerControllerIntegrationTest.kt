package org.hooney.petclinic.api.v1

import com.github.javafaker.Faker
import org.hooney.petclinic.constants.Profile
import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.repository.OwnerRepository
import org.hooney.petclinic.test_utils.HttpBodyBuilder
import org.hooney.petclinic.utils.unwrap
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
@ActiveProfiles(Profile.TEST)
@DisplayName("OwnerController")
class OwnerControllerIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var ownerRepository: OwnerRepository

    @Nested
    @DisplayName("POST /api/v1/owners")
    inner class CreateOwner {

        @Test
        @DisplayName("성공")
        fun createOwner() {
            val firstName = Faker().pokemon().name()
            val lastName = Faker().pokemon().name()
            val address = Faker().address().fullAddress()
            val telephone = Faker().number().digits(10)

            assertEquals(ownerRepository.count(), 0)
            val action = mockMvc.perform(post("/api/v1/owners")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(HttpBodyBuilder(
                            "firstName" to firstName,
                            "lastName" to lastName,
                            "address" to address,
                            "telephone" to telephone
                    ).build())
            )
            action.andExpect(status().isCreated)
            assertEquals(ownerRepository.count(), 1)
        }
    }

    @Nested
    @DisplayName("PUT /api/v1/owners/{ownerId}")
    inner class PutOwner {
        @Test
        @DisplayName("성공")
        fun putOwner() {
            val wasFirstName = Faker().pokemon().name()
            val wasLastName = Faker().pokemon().name()
            val wasAddress = Faker().address().fullAddress()
            val wasTelephone = Faker().number().digits(10)
            val owner = Owner(
                    firstName = wasFirstName,
                    lastName = wasLastName,
                    address = wasAddress,
                    telephone = wasTelephone
            )
            ownerRepository.save(owner)

            val firstName = Faker().pokemon().name()
            val lastName = Faker().pokemon().name()
            val address = Faker().address().fullAddress()
            val telephone = Faker().number().digits(10)
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
            val firstName = Faker().pokemon().name()
            val lastName = Faker().pokemon().name()
            val address = Faker().address().fullAddress()
            val telephone = Faker().number().digits(10)
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
        @Test
        @DisplayName("성공")
        fun deleteOwner() {
            val firstName = Faker().pokemon().name()
            val lastName = Faker().pokemon().name()
            val address = Faker().address().fullAddress()
            val telephone = Faker().number().digits(10)
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

}