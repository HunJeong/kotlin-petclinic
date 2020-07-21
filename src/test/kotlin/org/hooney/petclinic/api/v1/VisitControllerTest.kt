package org.hooney.petclinic.api.v1

import com.github.javafaker.Faker
import org.hooney.petclinic.entity.Visit
import org.hooney.petclinic.service.VisitService
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
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@WebMvcTest(VisitController::class)
class VisitControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var visitService: VisitService

    @Test
    fun getVisits() {
        //given
        val ownerId = Faker().number().randomNumber()
        val petId = Faker().number().randomNumber()
        val visit = Visit(
            description = Faker().lorem().sentence(),
            date = LocalDate.now(),
            petId = petId
        )
        given(visitService.getVisits(ownerId, petId))
            .willReturn(listOf(visit))

        //when
        val action = mockMvc.perform(get("/api/v1/owners/$ownerId/pets/$petId/visits"))

        //then
        action.andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$[0].petId").value(petId))
            .andExpect(jsonPath("$[0].description").value(visit.description!!))
    }

    @Test
    fun postVisits() {
        //given
        val ownerId = Faker().number().randomNumber()
        val petId = Faker().number().randomNumber()
        val description = Faker().lorem().sentence()
        val date = Faker().date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val dateString = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val visit = Visit(petId = petId, description = description, date = date)
        given(visitService.createVisit(ownerId, petId, description, date))
            .willReturn(visit)

        //when
        val action = mockMvc.perform(post("/api/v1/owners/$ownerId/pets/$petId/visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                HttpBodyBuilder(
                    "description" to description,
                    "date" to dateString
                ).build()
            ))

        //then
        action.andExpect(status().isCreated)
            .andExpect(jsonPath("$.description").value(description))
            .andExpect(jsonPath("$.date").value(dateString))
    }

    @Test
    fun postVisits_description_empty() {
        //given
        val ownerId = Faker().number().randomNumber()
        val petId = Faker().number().randomNumber()
        val description = ""
        val date = Faker().date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val dateString = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val visit = Visit(petId = petId, description = description, date = date)
        given(visitService.createVisit(ownerId, petId, description, date))
            .willReturn(visit)

        //when
        val action = mockMvc.perform(post("/api/v1/owners/$ownerId/pets/$petId/visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                HttpBodyBuilder(
                    "description" to description,
                    "date" to dateString
                ).build()
            ))

        //then
        action.andExpect(status().isBadRequest)
    }

}