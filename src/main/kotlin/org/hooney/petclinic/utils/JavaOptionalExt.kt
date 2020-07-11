package org.hooney.petclinic.utils

import java.util.*

fun <T> Optional<T>.unwrap() = if(isEmpty) { null } else { get() }