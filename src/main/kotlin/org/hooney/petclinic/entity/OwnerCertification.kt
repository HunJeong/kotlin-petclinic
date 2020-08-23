package org.hooney.petclinic.entity

import org.hibernate.annotations.DynamicUpdate
import org.hooney.petclinic.util.sha256
import javax.persistence.*
import javax.validation.constraints.Email

@Entity
@Table(name = "owner_certifications")
@DynamicUpdate
class OwnerCertification(
    @OneToOne
    @JoinColumn(name = "owner_id", updatable = false)
    var owner: Owner,

    @Column(name = "email", updatable = false)
    @Email
    var email: String,

    password: String
): Base() {
    @Column(name = "password")
    var digestedPassword: String = password.sha256()
}