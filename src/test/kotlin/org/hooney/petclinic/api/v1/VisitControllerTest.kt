package org.hooney.petclinic.api.v1

import com.github.javafaker.Faker
import org.hooney.petclinic.entity.Visit
import org.hooney.petclinic.service.VisitService
import org.hooney.petclinic.test_util.HttpBodyBuilder
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@WebMvcTest(VisitController::class)
@DisplayName("VisitController")
class VisitControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var visitService: VisitService

    @Nested
    @DisplayName("GET /api/v1/owners/{ownerId}/pets/{petId}/visits")
    inner class GetVisits {
        @Test
        @DisplayName("성공")
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
    }

    @Nested
    @DisplayName("POST /api/v1/owners/{ownerId}/pets/{petId}/visits")
    inner class PostVisits {
        @Test
        @DisplayName("성공")
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
        @DisplayName("description이 잘못됨")
        fun postVisitsDescriptionEmpty() {
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

    @Nested
    @DisplayName("PUT /api/v1/owners/{ownerId}/pets/{petId}/visits/{visitId}")
    inner class PutVisits {
        @Test
        @DisplayName("description이 잘못됨")
        fun putVisitsInvalidDescription() {
            val ownerId = Faker().number().randomNumber()
            val petId = Faker().number().randomNumber()
            val visitId = Faker().number().randomNumber()
            val wasDescription = ""
            val wasDate = Faker().date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            val wasDateString = wasDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

            val action = mockMvc.perform(
                    MockMvcRequestBuilders.put("/api/v1/owners/$ownerId/pets/$petId/visits/$visitId").contentType(MediaType.APPLICATION_JSON)
                            .content(
                                    HttpBodyBuilder(
                                            "description" to wasDescription,
                                            "date" to wasDateString
                                    ).build())
            )
            action.andExpect(status().isBadRequest)
        }
    }

}