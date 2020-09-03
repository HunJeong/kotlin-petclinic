package org.hooney.petclinic.test_util.fixture

import com.github.javafaker.Faker
import org.hooney.petclinic.entity.PetType

fun Fixture.petType(
    name: String = Faker().pokemon().name()
) = PetType(
    name = name
)