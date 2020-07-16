package org.hooney.petclinic.api.v1.response

class MessageResponse(val success: Boolean, val message: String, val data: Any? = null) {
}