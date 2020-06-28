package org.hooney.petclinic.entity

import javax.persistence.*

@Entity
@Table(name = "vets")
class Vet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "first_name")
    var firstName: String? = null,

    @Column(name = "last_name")
    var lastName: String? = null
) {
}