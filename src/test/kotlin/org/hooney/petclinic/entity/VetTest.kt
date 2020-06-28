package org.hooney.petclinic.entity

import com.github.javafaker.Faker
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class VetTest {

    @Test
    fun getFirstName() {
        val name = Faker().pokemon().name()
        val vet = Vet(firstName = name)
        assertEquals(vet.firstName, name)
    }

    @Test
    fun setFirstName() {
        val name = Faker().pokemon().name()
        val vet = Vet()
        assertEquals(vet.firstName, null)
        vet.firstName = name
        assertEquals(vet.firstName, name)
    }

    @Test
    fun getLastName() {
        val name = Faker().pokemon().name()
        val vet = Vet(lastName = name)
        assertEquals(vet.lastName, name)
    }

    @Test
    fun setLastName() {
        val name = Faker().pokemon().name()
        val vet = Vet()
        assertEquals(vet.lastName, null)
        vet.lastName = name
        assertEquals(vet.lastName, name)
    }
}