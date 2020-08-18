package org.hooney.petclinic.entity

import com.github.javafaker.Faker
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import java.time.LocalDate

@DisplayName("Pet")
class PetTest {

    @Nested
    @DisplayName("#getName")
    inner class GetName {
        @Test
        @DisplayName("성공")
        fun getName() {
            val name = Faker().pokemon().name()
            val pet = Pet(name = name)
            assertEquals(pet.name, name)
        }
    }

    @Nested
    @DisplayName("#setName")
    inner class SetName {
        @Test
        @DisplayName("성공")
        fun setName() {
            val name = Faker().pokemon().name()
            val pet = Pet()
            assertEquals(pet.name, null)
            pet.name = name
            assertEquals(pet.name, name)
        }
    }

    @Nested
    @DisplayName("getBirthDate")
    inner class GetBirthDate {
        @Test
        @DisplayName("성공")
        fun getBirthDate() {
            val birthDate = LocalDate.of(2020, 1, 1)
            val pet = Pet(birthDate = birthDate)
            assertEquals(pet.birthDate, birthDate)
        }
    }

    @Nested
    @DisplayName("setBirthDate")
    inner class SetBirthDate {
        @Test
        @DisplayName("성공")
        fun setBirthDate() {
            val birthDate = LocalDate.of(2020, 1, 1)
            val pet = Pet(null)
            assertEquals(pet.birthDate, null)
            pet.birthDate = birthDate
            assertEquals(pet.birthDate, birthDate)
        }
    }
}