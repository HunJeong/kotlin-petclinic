package org.hooney.petclinic.repository

import com.github.javafaker.Faker
import org.hooney.petclinic.constants.Profile
import org.hooney.petclinic.entity.Vet
import org.hamcrest.MatcherAssert.*
import org.hamcrest.Matchers.*
import org.hooney.petclinic.entity.Speciality
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import kotlin.streams.toList

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles(Profile.TEST)
@DisplayName("VetRepository")
class VetRepositoryTest {

    @Autowired
    lateinit var vetRepository: VetRepository

    @Autowired
    lateinit var specialityRepository: SpecialityRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Nested
    @DisplayName("#findAll")
    inner class FindAll {
//        @Test
//        @DisplayName("성공")
//        fun findAll() {
//            val speciality = Speciality(name = Faker().pokemon().name())
//            val speciality2 = Speciality(name = Faker().pokemon().name())
//            val speciality3 = Speciality(name = Faker().pokemon().name())
//
//            val vet = Vet(firstName = Faker().pokemon().name(), lastName = Faker().pokemon().name())
//            val vet2 = Vet(firstName = Faker().pokemon().name(), lastName = Faker().pokemon().name())
//
////            entityManager.persist(speciality)
////            entityManager.persist(speciality2)
////            entityManager.persist(speciality3)
//            specialityRepository.save(speciality)
//            specialityRepository.save(speciality2)
//            specialityRepository.save(speciality3)
//
//            vet.addSpecialty(speciality)
//            vet.addSpecialty(speciality2)
//            vet2.addSpecialty(speciality2)
//
//            vetRepository.save(vet)
//            vetRepository.save(vet2)
//            assertThat(vetRepository.findAll().stream().map { it.id }.toList(), contains(vet.id, vet2.id))
//
//            vet2.addSpecialty(speciality3)
//            vetRepository.save(vet2)
//            assertThat(vetRepository.findAll().stream().map { it.id }.toList(), contains(vet.id, vet2.id))
//        }

        @Test
        @DisplayName("specialities 없음")
        fun findAllNoSpecialities() {
            val vet = Vet(
                    firstName = Faker().pokemon().name(),
                    lastName = Faker().pokemon().name()
            )
            vetRepository.save(vet)
            assertThat(vetRepository.findAll().stream().map { it.id }.toList(), contains(vet.id))
        }
    }
}