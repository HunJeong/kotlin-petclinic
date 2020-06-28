package org.hooney.petclinic.entity

import com.github.javafaker.Faker
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

internal class PetTest {

    @Test
    fun getName() {
        val name = Faker().pokemon().name()
        val pet = Pet(name = name)
        assertEquals(pet.name, name)
    }

    @Test
    fun setName() {
        val name = Faker().pokemon().name()
        val pet = Pet()
        assertEquals(pet.name, null)
        pet.name = name
        assertEquals(pet.name, name)
    }

    @Test
    fun getBirthDate() {
        val birthDate = LocalDate.of(2020, 1, 1)
        val pet = Pet(birthDate = birthDate)
        assertEquals(pet.birthDate, birthDate)
    }

    @Test
    fun setBirthDate() {
        val birthDate = LocalDate.of(2020, 1, 1)
        val pet = Pet(null)
        assertEquals(pet.birthDate, null)
        pet.birthDate = birthDate
        assertEquals(pet.birthDate, birthDate)
    }
}