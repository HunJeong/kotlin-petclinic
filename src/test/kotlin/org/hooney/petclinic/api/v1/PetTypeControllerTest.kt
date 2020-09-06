package org.hooney.petclinic.api.v1

import org.hooney.petclinic.repository.AccessTokenRepository
import org.hooney.petclinic.service.PetTypeService
import org.hooney.petclinic.test_util.fixture.Fixture
import org.hooney.petclinic.test_util.fixture.petType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(PetTypeController::class)
@DisplayName("PetTypeController")
class PetTypeControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var petTypeService: PetTypeService

    @MockBean
    lateinit var accessTokenRepository: AccessTokenRepository

    @Nested
    @DisplayName("GET /api/v1/pet_types")
    inner class GetPetTypes {
        @Test
        @DisplayName("성공")
        fun getPetTypes() {
            //given
            val petType = Fixture.petType()
            given(petTypeService.findAll()).willReturn(listOf(petType))

            //when
            val action = mockMvc.perform(get("/api/v1/pet_types"))

            //then
            action.andExpect(status().isOk)
                    .andExpect(jsonPath("$").isArray)
                    .andExpect(jsonPath("$[0].name").value(petType.name!!))
        }
    }
}