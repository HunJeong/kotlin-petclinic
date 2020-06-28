package org.hooney.petclinic.entity

import javax.persistence.*

@Entity
@Table(name="types")
class PetType(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "name")
    var name: String? = null
)