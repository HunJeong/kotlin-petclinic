package org.hooney.petclinic.entity

import com.github.javafaker.Faker
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

@DisplayName("Vet")
class VetTest {

    @Nested
    @DisplayName("#getFirstName")
    inner class GetFirstName {
        @Test
        @DisplayName("성공")
        fun getFirstName() {
            val name = Faker().pokemon().name()
            val vet = Vet(firstName = name)
            assertEquals(vet.firstName, name)
        }
    }

    @Nested
    @DisplayName("#setFirstName")
    inner class SetFirstName {
        @Test
        @DisplayName("성공")
        fun setFirstName() {
            val name = Faker().pokemon().name()
            val vet = Vet()
            assertEquals(vet.firstName, null)
            vet.firstName = name
            assertEquals(vet.firstName, name)
        }
    }

    @Nested
    @DisplayName("#getlastName")
    inner class GetLastName {
        @Test
        @DisplayName("성공")
        fun getLastName() {
            val name = Faker().pokemon().name()
            val vet = Vet(lastName = name)
            assertEquals(vet.lastName, name)
        }
    }

    @Nested
    @DisplayName("#setLastName")
    inner class SetLastName {
        @Test
        @DisplayName("성공")
        fun setLastName() {
            val name = Faker().pokemon().name()
            val vet = Vet()
            assertEquals(vet.lastName, null)
            vet.lastName = name
            assertEquals(vet.lastName, name)
        }
    }
}