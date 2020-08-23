package org.hooney.petclinic.util

import java.security.MessageDigest

fun String.sha256() = hashing("SHA-256")

private fun String.hashing(algorithm: String): String {
    return MessageDigest.getInstance(algorithm).digest(this.toByteArray()).fold("", { str, it -> str + "%02x".format(it) })
}