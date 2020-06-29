package org.hooney.petclinic.repository

import com.github.javafaker.Faker
import org.hooney.petclinic.entity.Owner
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
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
        assertNull(ownerRepository.findById(Long.MAX_VALUE))
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
        assertEquals(ownerRepository.findById(owner.id as Long)!!.id, owner.id!!)
    }

    @Test
    fun findByLastName() {
        val lastName = Faker().pokemon().name()
        var owner = Owner(
            firstName = Faker().pokemon().name(),
            lastName = lastName,
            address = Faker().address().fullAddress(),
            city = Faker().address().city(),
            telephone = Faker().number().digits(10)
        )
        var owner2 = Owner(
            firstName = Faker().pokemon().name(),
            lastName = Faker().leagueOfLegends().champion(),
            address = Faker().address().fullAddress(),
            city = Faker().address().city(),
            telephone = Faker().number().digits(10)
        )
        owner = entityManager.persist(owner)
        owner2 = entityManager.persist(owner2)

        val owners = ownerRepository.findByLastName(lastName)
        assertTrue(owners.contains(owner))
        assertFalse(owners.contains(owner2))
    }

}