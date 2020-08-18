package org.hooney.petclinic.repository

import com.github.javafaker.Faker
import org.hooney.petclinic.constants.Profile
import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.utils.unwrap
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles(Profile.TEST)
@DisplayName("OwnerRepository")
class OwnerRepositoryTest {

    @Autowired
    lateinit var ownerRepository: OwnerRepository

    @Nested
    @DisplayName("#save")
    inner class Save {
        @Test
        @DisplayName("성공")
        fun save() {
            val owner = Owner(
                    firstName = Faker().pokemon().name(),
                    lastName = Faker().pokemon().name(),
                    address = Faker().address().fullAddress(),
                    city = Faker().address().city(),
                    telephone = Faker().phoneNumber().phoneNumber()
            )
            ownerRepository.save(owner)
            assertNotNull(owner.id)
        }
    }

    @Nested
    @DisplayName("#findById")
    inner class FindById {
        @Test
        @DisplayName("성공")
        fun findById() {
            var owner = Owner(
                    firstName = Faker().pokemon().name(),
                    lastName = Faker().pokemon().name(),
                    address = Faker().address().fullAddress(),
                    city = Faker().address().city(),
                    telephone = Faker().number().digits(10)
            )
            owner = ownerRepository.save(owner)
            assertEquals(ownerRepository.findById(owner.id as Long).unwrap()!!.id, owner.id!!)
        }

        @Test
        @DisplayName("데이터 없음")
        fun findByIdNoData() {
            val owner = Owner(
                    firstName = Faker().pokemon().name(),
                    lastName = Faker().pokemon().name(),
                    address = Faker().address().fullAddress(),
                    city = Faker().address().city(),
                    telephone = Faker().number().digits(10)
            )
            ownerRepository.save(owner)
            assertNull(ownerRepository.findById(Long.MAX_VALUE).unwrap())
        }
    }

    @Nested
    @DisplayName("#findAllByLastName")
    inner class FindAllByLastName {
        @Test
        @DisplayName("성공")
        fun findAllByLastName() {
            val lastName = Faker().pokemon().name()
            var owner = Owner(
                    firstName = Faker().pokemon().name(),
                    lastName = lastName,
                    address = Faker().address().fullAddress(),
                    city = Faker().address().city(),
                    telephone = Faker().number().digits(10)
            )
            owner = ownerRepository.save(owner)

            val resultOwner = ownerRepository.findByLastName(lastName)
            assertTrue(resultOwner!!.id == owner.id)
        }
    }

    @Nested
    @DisplayName("#deleteById")
    inner class DeleteById {
        @Test
        @DisplayName("성공")
        fun deleteById() {
            var owner = Owner(
                    firstName = Faker().pokemon().name(),
                    lastName = Faker().pokemon().name(),
                    address = Faker().address().fullAddress(),
                    city = Faker().address().city(),
                    telephone = Faker().number().digits(10)
            )
            owner = ownerRepository.save(owner)
            ownerRepository.deleteById(owner.id!!)
            assertNull(ownerRepository.findById(owner.id!!).unwrap())
        }

        @Test
        @DisplayName("데이터 없음")
        fun deleteByIdNotFound() {
            val id = Faker().number().randomNumber()
            assertThrows(EmptyResultDataAccessException::class.java) { ownerRepository.deleteById(id) }
        }
    }
}