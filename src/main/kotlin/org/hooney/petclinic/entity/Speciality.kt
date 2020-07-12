package org.hooney.petclinic.entity

import javax.persistence.*

@Entity
@Table(name = "specialities")
class Speciality(
        @Column(name = "name")
        var name: String? = null
): Base()