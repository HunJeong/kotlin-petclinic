package org.hooney.petclinic.test_util.fixture

import com.github.javafaker.Faker
import org.hooney.petclinic.entity.Owner

fun Fixture.owner(
    firstName: String = Faker().pokemon().name(),
    lastName: String = Faker().pokemon().name(),
    address: String = Faker().address().fullAddress(),
    city: String = Faker().address().city(),
    telephone: String? = Faker().number().digits(12)
) = Owner(
    firstName = firstName,
    lastName = lastName,
    address = address,
    city = city,
    telephone = telephone
)