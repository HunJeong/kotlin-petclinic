package org.hooney.petclinic.api.v1

import org.hooney.petclinic.entity.Vet
import org.hooney.petclinic.repository.VetRepository
import org.hooney.petclinic.service.VetService
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

@WebMvcTest(VetController::class)
@DisplayName("VetController")
class VetControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var vetService: VetService

    @Nested
    @DisplayName("GET /api/v1/vets")
    inner class GetVets {
        @Test
        @DisplayName("성공")
        fun getVets() {
            //given
            given(vetService.getAllVets())
                    .willReturn(listOf(
                            Vet("World", "Hello").also { it.id = 1 },
                            Vet("World", "Hell").also { it.id = 2 }
                    ))

            //when
            val action = mockMvc.perform(get("/api/v1/vets"))

            //then
            action.andExpect(status().isOk)
                    .andExpect(jsonPath("$").isArray)
                    .andExpect(jsonPath("$[0].firstName").value("World"))
                    .andExpect(jsonPath("$[0].lastName").value("Hello"))
                    .andExpect(jsonPath("$[1].firstName").value("World"))
                    .andExpect(jsonPath("$[1].lastName").value("Hell"))
        }

        @Test
        @DisplayName("데이터 없음")
        fun getVetsNoData() {
            //given
            given(vetService.getAllVets())
                    .willReturn(listOf())

            //when
            val action = mockMvc.perform(get("/api/v1/vets"))

            //then
            action.andExpect(status().isOk)
                    .andExpect(content().json("[]"))
        }
    }
}