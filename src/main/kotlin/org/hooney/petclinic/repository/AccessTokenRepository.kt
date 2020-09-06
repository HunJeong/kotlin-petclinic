package org.hooney.petclinic.repository

import org.hooney.petclinic.entity.AccessToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AccessTokenRepository: JpaRepository<AccessToken, Long> {

    @Query(value = "SELECT * FROM access_tokens WHERE token = :token LIMIT 1", nativeQuery = true)
    fun findByToken(@Param(value = "token") token: String): AccessToken?

}