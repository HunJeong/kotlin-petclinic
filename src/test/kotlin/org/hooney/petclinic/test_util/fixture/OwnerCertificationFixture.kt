package org.hooney.petclinic.test_util.fixture

import com.github.javafaker.Faker
import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.entity.OwnerCertification

fun Fixture.ownerCertification(
    owner: Owner = Fixture.owner(),
    email: String = Faker().internet().emailAddress(),
    password: String = Faker().internet().password()
) = OwnerCertification(
    owner = owner,
    email = email,
    password = password
)