package org.hooney.petclinic.repository

import com.github.javafaker.Faker
import org.hooney.petclinic.constants.Profile
import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.utils.unwrap
import org.junit.jupiter.api.Assertions.*
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
class OwnerRepositoryTest {

    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var ownerRepository: OwnerRepository

    @Test
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

    @Test
    fun findById_없음() {
        val owner = Owner(
            firstName = Faker().pokemon().name(),
            lastName = Faker().pokemon().name(),
            address = Faker().address().fullAddress(),
            city = Faker().address().city(),
            telephone = Faker().number().digits(10)
        )
        entityManager.persist(owner)
        assertNull(ownerRepository.findById(Long.MAX_VALUE).unwrap())
    }

    @Test
    fun findById_있음() {
        var owner = Owner(
            firstName = Faker().pokemon().name(),
            lastName = Faker().pokemon().name(),
            address = Faker().address().fullAddress(),
            city = Faker().address().city(),
            telephone = Faker().number().digits(10)
        )
        owner = entityManager.persist(owner)
        assertEquals(ownerRepository.findById(owner.id as Long).unwrap()!!.id, owner.id!!)
    }

    @Test
    fun findAllByLastName() {
        val lastName = Faker().pokemon().name()
        var owner = Owner(
            firstName = Faker().pokemon().name(),
            lastName = lastName,
            address = Faker().address().fullAddress(),
            city = Faker().address().city(),
            telephone = Faker().number().digits(10)
        )
        owner = entityManager.persist(owner)

        val resultOwner = ownerRepository.findByLastName(lastName)
        assertTrue(resultOwner!!.id == owner.id)
    }

    @Test
    fun deleteById() {
        var owner = Owner(
            firstName = Faker().pokemon().name(),
            lastName = Faker().pokemon().name(),
            address = Faker().address().fullAddress(),
            city = Faker().address().city(),
            telephone = Faker().number().digits(10)
        )
        owner = entityManager.persist(owner)
        ownerRepository.deleteById(owner.id!!)
        assertNull(ownerRepository.findById(owner.id!!).unwrap())
    }

    @Test
    fun deleteById_NotFound() {
        val id = Faker().number().randomNumber()
        assertThrows(EmptyResultDataAccessException::class.java) { ownerRepository.deleteById(id) }
    }
}