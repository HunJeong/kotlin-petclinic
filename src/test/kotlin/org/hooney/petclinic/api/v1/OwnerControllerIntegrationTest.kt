package org.hooney.petclinic.api.v1

import com.github.javafaker.Faker
import org.hooney.petclinic.constants.Profile
import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.repository.OwnerRepository
import org.hooney.petclinic.test_utils.HttpBodyBuilder
import org.hooney.petclinic.utils.unwrap
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.HandlerResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles(Profile.TEST)
class OwnerControllerIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var ownerRepository: OwnerRepository

    @Test
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

    @Test
    fun createOwner_miss_required_param() {
        val lastName = Faker().pokemon().name()
        val address = Faker().address().fullAddress()
        val telephone = Faker().number().digits(10)

        val action = mockMvc.perform(post("/api/v1/owners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(HttpBodyBuilder(
                "lastName" to lastName,
                "address" to address,
                "telephone" to telephone
            ).build())
        )
        action.andExpect(status().isBadRequest)
    }

    @Test
    fun createOwner_telephone_too_long() {
        val firstName = Faker().pokemon().name()
        val lastName = Faker().pokemon().name()
        val address = Faker().address().fullAddress()
        val telephone = Faker().number().digits(15)

        val action = mockMvc.perform(post("/api/v1/owners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(HttpBodyBuilder(
                "firstName" to firstName,
                "lastName" to lastName,
                "address" to address,
                "telephone" to telephone
            ).build())
        )

        action.andExpect(status().isBadRequest)
        assertEquals(ownerRepository.count(), 0)
    }

    @Test
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
    fun putOwner_owner_not_found() {
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

    @Test
    fun putOwner_owner_invalid_body() {
        val firstName = Faker().pokemon().name()
        val lastName = Faker().pokemon().name()
        val address = Faker().address().fullAddress()
        val telephone = Faker().number().digits(20)
        val action = mockMvc.perform(put("/api/v1/owners/999").contentType(MediaType.APPLICATION_JSON)
            .content(HttpBodyBuilder(
                "firstName" to firstName,
                "lastName" to lastName,
                "address" to address,
                "telephone" to telephone
            ).build())
        )
        action.andExpect(status().isBadRequest)
    }

    @Test
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
    fun deleteOnwer_no_data() {
        val id = Faker().number().randomNumber()
        mockMvc.perform(delete("/api/v1/owners/${id}"))
            .andExpect(status().isNotFound)
    }

}