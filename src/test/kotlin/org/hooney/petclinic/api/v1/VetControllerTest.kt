package org.hooney.petclinic.api.v1

import org.hooney.petclinic.entity.Vet
import org.hooney.petclinic.repository.VetRepository
import org.hooney.petclinic.service.VetService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(VetController::class, VetService::class)
class VetControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var vetRepository: VetRepository

    @Test
    fun getVets_데이터가_없으면_빈_배열() {
        //given
        given(this.vetRepository.findAll())
            .willReturn(listOf())

        //when
        val action = mockMvc.perform(get("/api/v1/vets"))

        //then
        action.andExpect(status().isOk)
            .andExpect(content().json("[]"))
    }

    @Test
    fun getVets_데이터가_있음() {
        //given
        given(this.vetRepository.findAll())
            .willReturn(listOf(
                Vet(1, "World", "Hello"),
                Vet(2, "World", "Hell")
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
}