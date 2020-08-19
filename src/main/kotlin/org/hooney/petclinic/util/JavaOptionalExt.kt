package org.hooney.petclinic.util

import java.util.*

fun <T> Optional<T>.unwrap() = if(isEmpty) { null } else { get() }