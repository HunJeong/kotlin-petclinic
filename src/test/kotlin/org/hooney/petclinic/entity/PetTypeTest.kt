package org.hooney.petclinic.entity

import com.github.javafaker.Faker
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("PetType")
class PetTypeTest {

    @Nested
    @DisplayName("#getName")
    inner class GetName {
        @Test
        @DisplayName("성공")
        fun getName() {
            val name = Faker().pokemon().name()
            val petType = PetType(name).also { it.id = 1 }
            assertEquals(petType.name, name)
        }
    }

    @Nested
    @DisplayName("#setName")
    inner class SetName {
        @Test
        @DisplayName("성공")
        fun setName() {
            val name = Faker().pokemon().name()
            val petType = PetType()

            assertEquals(petType.name, null)
            petType.name = name
            assertEquals(petType.name, name)
        }
    }
}