package org.hooney.petclinic.api.v1.response

import org.hooney.petclinic.annotation.NoArgConstructor

@NoArgConstructor
class MessageResponse(val success: Boolean, val message: String, val data: Any? = null) {
}