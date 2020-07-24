package org.hooney.petclinic.repository

import org.hooney.petclinic.entity.Pet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface PetRepository: JpaRepository<Pet, Long> {

    @Query(value = "SELECT * FROM pets where owner_id = :ownerId", nativeQuery = true)
    @Transactional(readOnly = true)
    fun findAllByOwnerIdWithoutOwner(@Param("ownerId") ownerId: Long): List<Pet>

    @Query(value = "SELECT * FROM pets WHERE id = :id AND owner_id = :ownerId", nativeQuery = true)
    @Transactional(readOnly = true)
    fun findByIdAndOwnerId(@Param("id") id: Long, @Param("ownerId") ownerId: Long): Pet?
}