package org.hooney.petclinic.runner

import org.hooney.petclinic.entity.PetType
import org.hooney.petclinic.repository.PetTypeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

// 실제로 이렇게 사용 안 하겠지만... Seed값을 넣어주기 위해 만듦
@Component
class PetTypeSeedRunner(): ApplicationRunner {

    @Autowired
    lateinit var petTypeRepository: PetTypeRepository

    override fun run(args: ApplicationArguments?) {
        arrayOf(
            PetType("Jindo"),
            PetType("Sapsal"),
            PetType("Poodle"),
            PetType("Mix")
        ).map { petType -> petTypeRepository.findByName(petType.name!!) ?: petTypeRepository.save(petType) }
    }
}