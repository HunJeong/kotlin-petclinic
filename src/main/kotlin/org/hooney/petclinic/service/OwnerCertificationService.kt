package org.hooney.petclinic.service

import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.entity.OwnerCertification
import org.hooney.petclinic.exception.ExistedEmailException
import org.hooney.petclinic.exception.OwnerNotFoundException
import org.hooney.petclinic.repository.OwnerCertificationRepository
import org.springframework.stereotype.Service

@Service
class OwnerCertificationService(
    val ownerCertificationRepository: OwnerCertificationRepository
) {

    fun createOwnerCertification(owner: Owner, email: String, password: String): OwnerCertification {
        if (owner.isNew()) { throw OwnerNotFoundException() }
        if (ownerCertificationRepository.existsByEmail(email)) { throw ExistedEmailException() }
        return OwnerCertification(owner, email, password).let { ownerCertificationRepository.save(it) }
    }

}