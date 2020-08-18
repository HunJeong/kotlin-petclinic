package org.hooney.petclinic.entity

import com.github.javafaker.Faker
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.springframework.test.util.ReflectionTestUtils

@DisplayName("Owner")
class OwnerTest {

    @Nested
    @DisplayName("#fulName")
    inner class FullName {
        @Test
        @DisplayName("성공")
        fun fullName() {
            val firstName = Faker().pokemon().name()
            val lastName = Faker().pokemon().name()
            val owner = Owner(firstName = firstName, lastName = lastName)
            assertEquals(owner.fullName(), "$firstName $lastName".trim())
        }

        @Test
        @DisplayName("성만 있음")
        fun fullNameOnlyLastName() {
            val firstName = Faker().pokemon().name()
            val lastName = null
            val owner = Owner(firstName = firstName, lastName = lastName)
            assertEquals(owner.fullName(), firstName)
        }

        @Test
        @DisplayName("이름만 있음")
        fun fullNameOnlyFirstName() {
            val firstName = null
            val lastName = Faker().pokemon().name()
            val owner = Owner(firstName = firstName, lastName = lastName)
            assertEquals(owner.fullName(), lastName)
        }

        @Test
        @DisplayName("아무것도 없음")
        fun fullNameNoName() {
            val firstName = null
            val lastName = null
            val owner = Owner(firstName = firstName, lastName = lastName)
            assertEquals(owner.fullName(), "")
        }
    }

    @Nested
    @DisplayName("#getPetsInteral")
    inner class GetPetsInternal {
        @Test
        @DisplayName("성공")
        fun getPetsInternal() {
            val owner = Owner()
            val pets = ReflectionTestUtils.invokeMethod(owner, "getPetsInternal") as MutableSet<Pet>
            assertTrue(pets.isEmpty())
        }
    }

    @Nested
    @DisplayName("#setPetsInternal")
    inner class SetPetsInteral {
        @Test
        @DisplayName("성공")
        fun setPetsInternal() {
            val owner = Owner()
            val pets = mutableSetOf(Pet())
            ReflectionTestUtils.invokeMethod(owner, "setPetsInternal", pets) as? Unit
            val gotPets = ReflectionTestUtils.invokeMethod(owner, "getPetsInternal") as MutableSet<Pet>
            assertTrue(gotPets === pets)
        }
    }

    @Nested
    @DisplayName("#getPets")
    inner class GetPets {
        @Test
        @DisplayName("성공")
        fun getPets() {
            val pet1 = Pet("Puppy").also { it.id = 1 }
            val pet2 = Pet("Dog").also { it.id = 2 }
            val pets = mutableSetOf(pet1, pet2)
            val owner = Owner(pets = pets)

            owner.getPets().let {
                assertTrue(it[0].id == pet2.id)
                assertTrue(it[1].id == pet1.id)
            }
        }
    }

    @Nested
    @DisplayName("#addPet")
    inner class AddPet {
        @Test
        @DisplayName("새 Pet 추가")
        fun addPetNewPet() {
            val name = Faker().pokemon().name()
            val pet = Pet(name = name)
            val owner = Owner().also { it.id = 1 }

            assertNull(owner.getPet(name))
            owner.addPet(pet)
            assertNotNull(owner.getPet(name))
            assertTrue(pet.owner === owner)
        }

        @Test
        @DisplayName("기존에 있던 Pet 추가")
        fun addPetPersistentPet() {
            val name = Faker().pokemon().name()
            val pet = Pet(name = name).also { it.id = 1 }
            val owner = Owner().also { it.id = 1 }

            assertNull(owner.getPet(name))
            owner.addPet(pet)
            assertNull(owner.getPet(name))
            assertTrue(pet.owner === owner)
        }
    }

    @Nested
    @DisplayName("GetPet")
    inner class GetPet {
        @Test
        @DisplayName("이름으로 동물 찾기")
        fun getPetWithName() {
            val name = Faker().pokemon().name()
            val pet = Pet(name).also { it.id = 1 }
            val owner = Owner(pets = mutableSetOf(pet))

            assertTrue(owner.getPet(name.toUpperCase()) === pet)
        }

        @Test
        @DisplayName("persistent pet아니면 무시")
        fun getPetIgnoreNewIsTrue() {
            val name = Faker().pokemon().name()
            val pet = Pet(name = name)
            val owner = Owner(pets = mutableSetOf(pet))
            assertTrue(owner.getPet(name.toUpperCase(), ignoreNew = true) === null)
        }
    }
}