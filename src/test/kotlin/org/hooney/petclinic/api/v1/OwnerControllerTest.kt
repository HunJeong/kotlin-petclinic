package org.hooney.petclinic.api.v1

import com.github.javafaker.Faker
import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.repository.OwnerRepository
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@WebMvcTest(OwnerController::class)
class OwnerControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var ownerRepository: OwnerRepository


    @Test
    fun getOwners_empty() {
        //given
        given(ownerRepository.findAll())
            .willReturn(listOf())

        //when
        val action = mockMvc.perform(get("/api/v1/owners"))

        //then
        action.andExpect(status().isOk)
            .andExpect(content().json("[]"))
    }

    @Test
    fun getOwners_notEmpty() {
        //given
        given(this.ownerRepository.findAll())
            .willReturn(listOf(
                Owner(1, "World", "Hello"),
                Owner(2, "World", "Hell")
            ))

        //when
        val action = mockMvc.perform(get("/api/v1/owners"))

        //then
        action.andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].firstName").value("World"))
            .andExpect(jsonPath("$[0].lastName").value("Hello"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].firstName").value("World"))
            .andExpect(jsonPath("$[1].lastName").value("Hell"))
    }

    @Test
    fun getOwner_exist() {
        val id = Faker().number().randomNumber()
        val owner = Owner(id)
        //given
        given(this.ownerRepository.findById(id))
            .willReturn(owner)

        //when
        val action = mockMvc.perform(get("/api/v1/owners/$id"))

        //then
        action.andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(id))
    }

    @Test
    fun getOwner_notExist() {
        val id = Faker().number().randomNumber()
        //given
        given(this.ownerRepository.findById(id))
            .willReturn(null)

        //when
        val action = mockMvc.perform(get("/api/v1/$id"))

        //then
        action.andExpect(status().isNotFound)
    }
}
