package org.hooney.petclinic.repository

import org.hooney.petclinic.entity.Owner
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface OwnerRepository: JpaRepository<Owner, Long> {

    @Query(value = "SELECT owner FROM Owner owner left join fetch owner.pets WHERE owner.id =:id")
    @Transactional(readOnly = true)
    override fun findById(@Param("id") id: Long): Optional<Owner>

    @Query(value = "SELECT DISTINCT owner FROM Owner owner left join fetch owner.pets WHERE owner.lastName LIKE :lastName%")
    @Transactional(readOnly = true)
    fun findByLastName(@Param("lastName") lastName: String): Collection<Owner>
}