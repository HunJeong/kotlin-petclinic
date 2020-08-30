package org.hooney.petclinic.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.iso8601():String = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(this)