package org.hooney.petclinic.api.v1

import org.hooney.petclinic.repository.OwnerRepository
import org.springframework.web.bind.annotation.RestController

@RestController
class OwnerController(val ownerRepository: OwnerRepository) {
}