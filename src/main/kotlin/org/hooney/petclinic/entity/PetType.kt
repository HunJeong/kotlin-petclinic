package org.hooney.petclinic.entity

import javax.persistence.*

@Entity
@Table(name="types")
class PetType(
    @Column(name = "name")
    var name: String? = null
): Base()