package org.hooney.petclinic.entity

import org.hooney.petclinic.util.TokenGenerator
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "access_tokens")
class AccessToken(
    @Column(name = "token")
    @NotEmpty
    val token: String = TokenGenerator.generateToken(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    var owner: Owner,

    @Column(name = "expire_at")
    var expireAt: LocalDateTime? = defaultExpireAt()
): Base() {

    companion object {
        private fun defaultExpireAt() = LocalDateTime.now().plusHours(6)
    }

    fun isExpire() = expireAt?.let { it < LocalDateTime.now() } ?: false

}