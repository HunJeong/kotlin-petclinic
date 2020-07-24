package org.hooney.petclinic.service

import org.hooney.petclinic.api.v1.response.OwnerResponse
import org.hooney.petclinic.entity.Owner
import org.hooney.petclinic.repository.OwnerRepository
import org.hooney.petclinic.utils.unwrap
import org.springframework.stereotype.Service

@Service
class OwnerService(val ownerRepository: OwnerRepository) {

    fun getOwners() = ownerRepository.findAll()

    fun getOwner(id: Long) = ownerRepository.findById(id).unwrap()

    fun createOwner(
            firstName: String,
            lastName: String,
            address: String,
            city: String?,
            telephone: String
    ): Owner = Owner(
            firstName = firstName,
            lastName = lastName,
            address = address,
            city = city,
            telephone = telephone
    ).let { ownerRepository.save(it) }

    fun putOwner(
            id: Long,
            firstName: String?,
            lastName: String?,
            address: String?,
            city: String?,
            telephone: String?
    ) = ownerRepository.findById(id).unwrap()?.apply {
        firstName?.let { this.firstName = firstName }
        lastName?.let { this.lastName = lastName }
        address?.let { this.address = address }
        city?.let { this.city = city }
        telephone?.let { this.telephone = telephone }
        ownerRepository.save(this)
    }

    fun deleteOwner(id: Long) {
        ownerRepository.deleteById(id)
    }
}