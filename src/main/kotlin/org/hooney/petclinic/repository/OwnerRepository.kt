package org.hooney.petclinic.repository

import org.hooney.petclinic.entity.Owner
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface OwnerRepository: JpaRepository<Owner, Long> {

    @Query(value = "SELECT * FROM owners WHERE id = :id", nativeQuery = true)
    @Transactional(readOnly = true)
    override fun findById(@Param("id") id: Long): Optional<Owner>

    @Query(value = "SELECT * FROM owners WHERE last_name = :last_name", nativeQuery = true)
    @Transactional(readOnly = true)
    fun findByLastName(@Param("last_name") lastName: String): Owner?
}