package org.hooney.petclinic.entity

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "visits")
class Visit(
    @Column(name = "visit_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var date: LocalDate? = null,

    @Column(name = "description")
    @NotEmpty
    var description: String? = null,

    @Column(name = "pet_id")
    var petId: Long? = null
    ): Base() {
}