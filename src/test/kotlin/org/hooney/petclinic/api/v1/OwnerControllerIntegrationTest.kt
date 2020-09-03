package org.hooney.petclinic.api.v1

import com.github.javafaker.Faker
import org.hooney.petclinic.api.v1.response.MessageResponse
import org.hooney.petclinic.api.v1.response.OwnerResponse
import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.repository.OwnerRepository
import org.hooney.petclinic.test_util.HttpBodyBuilder
import org.hooney.petclinic.test_util.annotation.IntegrationTest
import org.hooney.petclinic.test_util.fixture.Fixture
import org.hooney.petclinic.test_util.fixture.owner
import org.hooney.petclinic.util.unwrap
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*

@IntegrationTest
@DisplayName("OwnerController")
class OwnerControllerIntegrationTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Autowired
    lateinit var ownerRepository: OwnerRepository

    @Nested
    @DisplayName("PUT /api/v1/owners/{ownerId}")
    inner class PutOwner {
        val firstName = Faker().pokemon().name()
        val lastName = Faker().pokemon().name()
        val address = Faker().address().fullAddress()
        val telephone = Faker().number().digits(10)

        var params = mutableMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "address" to address,
            "telephone" to telephone
        )

        inline fun <reified T> subject(ownerId: Long): ResponseEntity<T> {
            return testRestTemplate.exchange(
                "/api/v1/owners/${ownerId}",
                HttpMethod.PUT,
                HttpEntity(
                    HttpBodyBuilder(params).build(),
                    HttpHeaders().apply { this.contentType = MediaType.APPLICATION_JSON }
                ),
                T::class.java
            )
        }

        @Test
        @DisplayName("성공")
        fun putOwner() {
            val owner = Fixture.owner()
            ownerRepository.save(owner)

            val response = subject<OwnerResponse>(owner.id!!)

            assertEquals(response.statusCode, HttpStatus.OK)

            val updated_owner = ownerRepository.findById(owner.id!!).unwrap()
            assertEquals(updated_owner?.firstName, firstName)
            assertEquals(updated_owner?.lastName, lastName)
            assertEquals(updated_owner?.address, address)
            assertEquals(updated_owner?.telephone, telephone)
        }

        @Test
        @DisplayName("owner가 없음")
        fun putOwnerOwnerNotFound() {
            val response = subject<MessageResponse>(Long.MAX_VALUE)

            assertEquals(response.statusCode, HttpStatus.NOT_FOUND)
            assertEquals(response.body.success, false)
        }
    }

    @Nested
    @DisplayName("DELETE /api/1/owners/{ownerId}")
    inner class DeleteOwner {

        inline fun <reified T> subject(ownerId: Long): ResponseEntity<T> {
            return testRestTemplate.exchange(
                "/api/v1/owners/${ownerId}",
                HttpMethod.DELETE,
                HttpEntity(
                    HttpBodyBuilder().build(),
                    HttpHeaders().apply { this.contentType = MediaType.APPLICATION_JSON }
                ),
                T::class.java
            )
        }

        @Test
        @DisplayName("성공")
        fun deleteOwner() {
            val owner = Fixture.owner()
            ownerRepository.save(owner)

            val response = subject<OwnerResponse>(owner.id!!)
            assertEquals(response.statusCode, HttpStatus.NO_CONTENT)
            assertNull(ownerRepository.findById(owner.id!!).unwrap())
        }

        @Test
        @DisplayName("사용자 없음")
        fun deleteOwnerNoData() {
            val id = Faker().number().randomNumber()
            val response = subject<OwnerResponse>(id)
            assertEquals(response.statusCode, HttpStatus.NOT_FOUND)
        }
    }
}
