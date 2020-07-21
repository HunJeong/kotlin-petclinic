package org.hooney.petclinic.api.v1

import com.github.javafaker.Faker
import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.entity.Pet
import org.hooney.petclinic.entity.PetType
import org.hooney.petclinic.service.PetService
import org.hooney.petclinic.test_utils.HttpBodyBuilder
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@WebMvcTest(PetController::class, PetService::class)
class PetControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var petService: PetService

    @Test
    fun getPets() {
        //given
        val owner = Owner()
        owner.id = Faker().number().randomNumber()
        val pet1 = Pet(name = Faker().pokemon().name(), birthDate = LocalDate.now(), owner = owner)
        val pet2 = Pet(name = Faker().pokemon().name(), birthDate = LocalDate.now(), owner = owner)
        given(petService.getOwnerPets(owner.id!!))
            .willReturn(listOf(pet1, pet2))

        //when
        val action = mockMvc.perform(get("/api/v1/owners/${owner.id}/pets"))

        //then
        action.andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$[0].name").value(pet1.name!!))
            .andExpect(jsonPath("$[1].name").value(pet2.name!!))
    }

    @Test
    fun postPets() {
        //given
        val owner = Owner()
        owner.id = Faker().number().randomNumber()
        val name = Faker().pokemon().name()
        val birthDate = LocalDate.now()
        val birthDateString = birthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val type = Faker().pokemon().name()
        given(petService.createOwnerPets(owner.id!!, name, birthDate, type))
            .willReturn(Pet(name = name, birthDate = birthDate, type = PetType()))

        //when
        val action = mockMvc.perform(post("/api/v1/owners/${owner.id}/pets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(HttpBodyBuilder(
                "name" to name,
                "birthDate" to birthDateString,
                "type" to type
            ).build()))

        //then
        action.andExpect(status().isCreated)
            .andExpect(jsonPath("$.name").value(name))
            .andExpect(jsonPath("$.birthDate").value(birthDateString))
    }

}