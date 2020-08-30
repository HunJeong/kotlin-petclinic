package org.hooney.petclinic.repository

import org.hooney.petclinic.entity.AccessToken
import org.springframework.data.jpa.repository.JpaRepository

interface AccessTokenRepository: JpaRepository<AccessToken, Long> {
}