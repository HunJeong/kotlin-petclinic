package org.hooney.petclinic.entity

import javax.persistence.*

@Entity
@Table(name = "specialities")
class Speciality(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @Column(name = "name")
        var name: String? = null
) {
}