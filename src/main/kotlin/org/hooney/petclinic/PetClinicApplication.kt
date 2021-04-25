package org.hooney.petclinic

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(
    exclude = [DataSourceAutoConfiguration::class]
)
class PetClinicApplication

fun main(args: Array<String>) {
    runApplication<PetClinicApplication>(*args)
}