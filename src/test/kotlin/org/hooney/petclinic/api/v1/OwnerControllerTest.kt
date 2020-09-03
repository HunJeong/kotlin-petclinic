package org.hooney.petclinic.api.v1

import com.github.javafaker.Faker
import org.hooney.petclinic.service.OwnerCertificationService
import org.hooney.petclinic.service.OwnerService
import org.hooney.petclinic.test_util.HttpBodyBuilder
import org.hooney.petclinic.test_util.fixture.Fixture
import org.hooney.petclinic.test_util.fixture.owner
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@WebMvcTest(OwnerController::class)
@DisplayName("OwnerController")
class OwnerControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var ownerService: OwnerService

    @MockBean
    lateinit var ownerCertificationService: OwnerCertificationService

    @Nested
    @DisplayName("GET /api/v1/owners")
    inner class GetOwners {
        @Test
        @DisplayName("Owner가 없음")
        fun getOwnersEmpty() {
            //given
            given(ownerService.getOwners())
                .willReturn(listOf())

            //when
            val action = mockMvc.perform(get("/api/v1/owners"))

            //then
            action.andExpect(status().isOk)
                .andExpect(content().json("[]"))
        }

        @Test
        @DisplayName("Owner가 존재")
        fun getOwnersNotEmpty() {
            //given
            given(ownerService.getOwners())
                .willReturn(listOf(
                    Fixture.owner(firstName = "World", lastName = "Hello").also { it.id = 1 },
                    Fixture.owner(firstName = "World", lastName = "Hell").also { it.id = 2 }
                ))

            //when
            val action = mockMvc.perform(get("/api/v1/owners"))

            //then
            action.andExpect(status().isOk)
                .andExpect(jsonPath("$").isArray)
                .andExpect(jsonPath("$[0].firstName").value("World"))
                .andExpect(jsonPath("$[0].lastName").value("Hello"))
                .andExpect(jsonPath("$[1].firstName").value("World"))
                .andExpect(jsonPath("$[1].lastName").value("Hell"))
        }
    }

    @Nested
    @DisplayName("GET /api/v1/owners/{ownerId}")
    inner class GetOwner {

        val id = Faker().number().randomNumber()
        val owner = Fixture.owner().also { it.id = 1 }

        @Test
        @DisplayName("Owner 존재")
        fun getOwnerExist() {
            //given
            given(ownerService.getOwner(id))
                .willReturn(owner)

            //when
            val action = mockMvc.perform(get("/api/v1/owners/$id"))

            //then
            action.andExpect(status().isOk)
                .andExpect(jsonPath("$.firstName").value(owner.firstName!!))
        }

        @Test
        @DisplayName("Owner 없음")
        fun getOwnerNotExist() {
            //given
            given(ownerService.getOwner(id))
                .willReturn(null)

            //when
            val action = mockMvc.perform(get("/api/v1/$id"))

            //then
            action.andExpect(status().isNotFound)
        }
    }

    @Nested
    @DisplayName("PUT /api/v1/owners/{ownerId}")
    inner class PutOwner {
        val firstName = Faker().pokemon().name()
        val lastName = Faker().pokemon().name()
        val address = Faker().address().fullAddress()
        val telephone = Faker().number().digits(10)

        @Test
        @DisplayName("전화번호가 길 때")
        fun putOwnerOwnerInvalidBody() {
            val telephone = Faker().number().digits(20)
            val action = mockMvc.perform(
                put("/api/v1/owners/999").contentType(MediaType.APPLICATION_JSON)
                    .content(
                        HttpBodyBuilder(
                            "firstName" to firstName,
                            "lastName" to lastName,
                            "address" to address,
                            "telephone" to telephone
                        ).build()
                    )
            )
            action.andExpect(status().isBadRequest)
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
        val telephone = Faker().number().digits(14)

        @Test
        @DisplayName("이메일 포맷이 아님")
        fun signupOwnerInvalidEmailFormat() {
            val email = Faker().pokemon().name()
            val action = mockMvc.perform(
                post("/api/v1/owners/signup").contentType(MediaType.APPLICATION_JSON)
                    .content(
                        HttpBodyBuilder(
                            "email" to email,
                            "password" to password,
                            "firstName" to firstName,
                            "lastName" to lastName,
                            "address" to address,
                            "telephone" to telephone
                        ).build()
                    )
            )
            action.andExpect(status().isBadRequest)
        }

        @Test
        @DisplayName("비밀번호가 적정 길이가 아닐 때")
        fun signupOwnerInvalidPasswordLength() {
            val password = Faker().internet().password(5, 9)
            val action = mockMvc.perform(
                post("/api/v1/owners/signup").contentType(MediaType.APPLICATION_JSON)
                    .content(
                        HttpBodyBuilder(
                            "email" to email,
                            "password" to password,
                            "firstName" to firstName,
                            "lastName" to lastName,
                            "address" to address,
                            "telephone" to telephone
                        ).build()
                    )
            )
            action.andExpect(status().isBadRequest)
        }
    }

}
