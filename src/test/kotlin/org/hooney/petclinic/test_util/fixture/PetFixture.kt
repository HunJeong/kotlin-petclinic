package org.hooney.petclinic.test_util.fixture

import com.github.javafaker.Faker
import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.entity.Pet
import org.hooney.petclinic.entity.PetType
import java.time.LocalDate
import java.time.ZoneId

fun Fixture.pet(
    name: String = Faker().pokemon().name(),
    birthDate: LocalDate = Faker().date().birthday(1, 20).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
    owner: Owner? = null,
    type: PetType? = null
) = Pet(
    name = name,
    birthDate = birthDate,
    owner = owner,
    type = type
)