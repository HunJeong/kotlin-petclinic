package org.hooney.petclinic.service

import com.github.javafaker.Faker
import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.entity.OwnerCertification
import org.hooney.petclinic.exception.ExistedEmailException
import org.hooney.petclinic.exception.OwnerNotFoundException
import org.hooney.petclinic.repository.OwnerCertificationRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(classes = [OwnerCertificationService::class])
@DisplayName("OwnerCertificationService")
class OwnerCertificationServiceTest {

    @MockBean
    lateinit var ownerCertificationRepository: OwnerCertificationRepository

    @Autowired
    lateinit var ownerCertificationService: OwnerCertificationService

    @Nested
    @DisplayName("#createOwnerCertification")
    inner class CreateOwnerCertification {

        val owner = Owner().apply { this.id = Faker().number().randomNumber() }
        val email = Faker().internet().emailAddress()
        val password = Faker().internet().password()

        @DisplayName("owner가 db에 없음")
        @Test
        fun createOwnerCertificationNoOwner() {
            val owner = Owner()
            assertThrows(OwnerNotFoundException::class.java) { ownerCertificationService.createOwnerCertification(owner, email, password) }
        }

        @DisplayName("이미 존재하는 email")
        @Test
        fun createOwnerExistedEmail() {
            given(ownerCertificationRepository.existsByEmail(email))
                .willReturn(true)
            assertThrows(ExistedEmailException::class.java) { ownerCertificationService.createOwnerCertification(owner, email, password) }
        }
        
        @DisplayName("성공")
        @Test
        fun createOwner() {
            given(ownerCertificationRepository.existsByEmail(email))
                .willReturn(false)
            val ownerCertification = OwnerCertification(owner, email, password).apply { this.id = Faker().number().randomNumber() }
            given(ownerCertificationRepository.save(any(OwnerCertification::class.java)))
                .willReturn(ownerCertification)

            assertEquals(ownerCertificationService.createOwnerCertification(owner, email, password).id, ownerCertification.id)
        }
    }

}