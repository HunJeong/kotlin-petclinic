package org.hooney.petclinic.util

import org.apache.commons.lang3.RandomStringUtils
import java.security.SecureRandom

object TokenGenerator {

    private val secureRandom = SecureRandom()

    fun generateToken(length: Int = 32): String = RandomStringUtils.random(length, 0, 0, true, true, null, secureRandom)

}