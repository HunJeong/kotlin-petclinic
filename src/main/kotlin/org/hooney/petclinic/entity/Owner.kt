package org.hooney.petclinic.entity

import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*
import javax.validation.constraints.Digits
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "owners")
@DynamicUpdate
class Owner(
    @Column(name = "first_name")
    @NotEmpty
    var firstName: String? = null,

    @Column(name = "last_name")
    @NotEmpty
    var lastName: String? = null,

    @Column(name = "address")
    @NotEmpty
    var address: String? = null,

    @Column(name = "city")
    @NotEmpty
    var city: String? = null,

    @Column(name = "telephone")
    @NotEmpty
    var telephone: String? = null,

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], mappedBy = "owner")
    var pets: MutableSet<Pet> = mutableSetOf()
): Base() {

    fun fullName() = "${firstName ?: ""} ${lastName ?: ""}".trim()

    private fun getPetsInternal() = pets
    private fun setPetsInternal(pets: MutableSet<Pet>) { this.pets = pets }

    fun getPets(): List<Pet> = getPetsInternal().toList().sortedBy { it.name }

    fun addPet(pet: Pet) {
        if(pet.isNew()) { getPetsInternal().add(pet) }
        pet.owner = this
    }

    fun getPet(name: String, ignoreNew: Boolean = false): Pet? {
        val _name = name.toLowerCase()
        return getPetsInternal().filter { !ignoreNew || !it.isNew() }
            .firstOrNull { it.name?.toLowerCase() == _name }
    }
}