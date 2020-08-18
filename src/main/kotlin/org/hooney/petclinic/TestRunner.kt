package org.hooney.petclinic

import org.hooney.petclinic.repository.OwnerRepository
import org.hooney.petclinic.repository.PetRepository
import org.hooney.petclinic.repository.PetTypeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
@Transactional
class TestRunner(): ApplicationRunner {
//
//    @Autowired
//    lateinit var ownerRepository: OwnerRepository
//
//    @Autowired
//    lateinit var petTypeRepository: PetTypeRepository
//
//    @Autowired
//    lateinit var petRepository: PetRepository

    override fun run(args: ApplicationArguments?) {
    }
}