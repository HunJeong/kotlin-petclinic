package org.hooney.petclinic.repository

import org.hooney.petclinic.entity.Speciality
import org.springframework.data.jpa.repository.JpaRepository

interface SpecialityRepository: JpaRepository<Speciality, Long>