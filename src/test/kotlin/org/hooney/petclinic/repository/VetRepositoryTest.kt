package org.hooney.petclinic.repository

import com.github.javafaker.Faker
import org.hooney.petclinic.constants.Profile
import org.hooney.petclinic.entity.Vet
import org.hamcrest.MatcherAssert.*
import org.hamcrest.Matchers.*
import org.hooney.petclinic.entity.Speciality
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles(Profile.TEST)
class VetRepositoryTest {

    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var vetRepository: VetRepository

    @Test
    fun findAll_no_specialities() {
        val vet = Vet(
                firstName = Faker().pokemon().name(),
                lastName = Faker().pokemon().name()
        )
        entityManager.persist(vet)
        assertThat(vetRepository.findAll(), contains(vet))
    }

    @Test
    fun findAll_have_specialities() {
        val speciality = Speciality(name = Faker().pokemon().name())
        val speciality2 = Speciality(name = Faker().pokemon().name())
        val speciality3 = Speciality(name = Faker().pokemon().name())
        val vet = Vet(firstName = Faker().pokemon().name(), lastName = Faker().pokemon().name())
        val vet2 = Vet(firstName = Faker().pokemon().name(), lastName = Faker().pokemon().name())
        vet.addSpecialty(speciality)
        vet.addSpecialty(speciality2)
        vet2.addSpecialty(speciality2)
        entityManager.persist(vet)
        entityManager.persist(vet2)
        assertThat(vetRepository.findAll(), contains(vet, vet2))
        vet2.addSpecialty(speciality3)
        entityManager.persist(vet2)
        assertThat(vetRepository.findAll(), contains(vet, vet2))
    }
}