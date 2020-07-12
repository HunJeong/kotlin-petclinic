package org.hooney.petclinic.entity

import com.github.javafaker.Faker
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.test.util.ReflectionTestUtils

internal class OwnerTest {

    @Test
    fun fullName() {
        val firstName = Faker().pokemon().name()
        val lastName = Faker().pokemon().name()
        val owner = Owner(firstName = firstName, lastName = lastName)
        assertEquals(owner.fullName(), "$firstName $lastName".trim())
    }

    @Test
    fun fullName_성만_있음() {
        val firstName = Faker().pokemon().name()
        val lastName = null
        val owner = Owner(firstName = firstName, lastName = lastName)
        assertEquals(owner.fullName(), firstName)
    }

    @Test
    fun fullName_이름만_있음() {
        val firstName = null
        val lastName = Faker().pokemon().name()
        val owner = Owner(firstName = firstName, lastName = lastName)
        assertEquals(owner.fullName(), lastName)
    }

    @Test
    fun fullName_아무것도_없음() {
        val firstName = null
        val lastName = null
        val owner = Owner(firstName = firstName, lastName = lastName)
        assertEquals(owner.fullName(), "")
    }

    @Test
    fun getPetsInternal() {
        val owner = Owner()
        val pets = ReflectionTestUtils.invokeMethod(owner, "getPetsInternal") as MutableSet<Pet>
        assertTrue(pets.isEmpty())
    }

    @Test
    fun setPetsInternal() {
        val owner = Owner()
        val pets = mutableSetOf(Pet())
        ReflectionTestUtils.invokeMethod(owner, "setPetsInternal", pets) as? Unit
        val gotPets = ReflectionTestUtils.invokeMethod(owner, "getPetsInternal") as MutableSet<Pet>
        assertTrue(gotPets === pets)
    }

    @Test
    fun getPets_이름_순_정렬() {
        val pet1 = Pet("Puppy").also { it.id = 1 }
        val pet2 = Pet("Dog").also { it.id = 2 }
        val pets = mutableSetOf(pet1, pet2)
        val owner = Owner(pets = pets)

        owner.getPets().let {
            assertTrue(it[0].id == pet2.id)
            assertTrue(it[1].id == pet1.id)
        }
    }

    @Test
    fun addPet_새_Pet() {
        val name = Faker().pokemon().name()
        val pet = Pet(name = name)
        val owner = Owner().also { it.id = 1 }

        assertNull(owner.getPet(name))
        owner.addPet(pet)
        assertNotNull(owner.getPet(name))
        assertTrue(pet.owner === owner)
    }

    @Test
    fun addPet_Persistence_Pet() {
        val name = Faker().pokemon().name()
        val pet = Pet(name = name).also { it.id = 1 }
        val owner = Owner().also { it.id = 1 }

        assertNull(owner.getPet(name))
        owner.addPet(pet)
        assertNull(owner.getPet(name))
        assertTrue(pet.owner === owner)
    }

    @Test
    fun getPet_이름으로() {
        val name = Faker().pokemon().name()
        val pet = Pet(name).also { it.id = 1 }
        val owner = Owner(pets = mutableSetOf(pet))

        assertTrue(owner.getPet(name.toUpperCase()) === pet)
    }

    @Test
    fun getPet_ignoreNew_true() {
        val name = Faker().pokemon().name()
        val pet = Pet(name = name)
        val owner = Owner(pets = mutableSetOf(pet))
        assertTrue(owner.getPet(name.toUpperCase(), ignoreNew = true) === null)
    }
}