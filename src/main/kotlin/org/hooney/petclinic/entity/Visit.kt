package org.hooney.petclinic.entity

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
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
    var petId: Int? = null
    ): Base() {
}