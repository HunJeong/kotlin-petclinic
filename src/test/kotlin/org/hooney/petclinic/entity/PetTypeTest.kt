package org.hooney.petclinic.entity

import com.github.javafaker.Faker
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PetTypeTest {

    @Test
    fun getName() {
        val name = Faker().pokemon().name()
        val petType = PetType(name).also { it.id = 1 }
        assertEquals(petType.name, name)
    }

    @Test
    fun setName() {
        val name = Faker().pokemon().name()
        val petType = PetType()

        assertEquals(petType.name, null)
        petType.name = name
        assertEquals(petType.name, name)
    }

}