package org.hooney.petclinic.entity

import javax.persistence.*

@Entity
@Table(name = "vets")
class Vet(
    @Column(name = "first_name")
    var firstName: String? = null,

    @Column(name = "last_name")
    var lastName: String? = null,

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(name = "vet_specialties",
            joinColumns = [JoinColumn(name = "vet_id")],
            inverseJoinColumns = [JoinColumn(name = "speciality_id")]
    )
    var specialties: MutableSet<Speciality> = mutableSetOf()
): Base() {

    private fun getSpecialitiesInternal() = specialties
    private fun setSpecialitiesInternal(specialties: MutableSet<Speciality>) { this.specialties = specialties }

    fun getSpecialities() = ArrayList(specialties).sortedBy { it.name }
    fun getNrOfSpecialties() = getSpecialitiesInternal().size
    fun addSpecialty(speciality: Speciality) { getSpecialitiesInternal().add(speciality) }
}