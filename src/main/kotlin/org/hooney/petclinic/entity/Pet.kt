package org.hooney.petclinic.entity

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "pets")
class Pet(
    @Column(name = "name")
    var name: String? = null,

    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var birthDate: LocalDate? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    var owner: Owner? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    var type: PetType? = null,

    @OneToMany(mappedBy = "pet", cascade = [CascadeType.ALL])
    var visits: MutableSet<Visit> = mutableSetOf()
): Base() {

    private fun getVisitsInternal() = visits
    private fun setVisitsInternal(visits: MutableSet<Visit>) { this.visits = visits }

    fun getVisits(): List<Visit> = getVisitsInternal().toList()

    fun addVisit(visit: Visit) {
        if(visit.isNew()) { getVisitsInternal().add(visit) }
        visit.pet = this
    }
}