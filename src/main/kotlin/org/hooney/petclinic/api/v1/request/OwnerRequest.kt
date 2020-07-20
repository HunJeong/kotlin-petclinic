package org.hooney.petclinic.api.v1.request

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class OwnerRequest {

    class OwnerCreateRequest(
        @get:NotNull val firstName: String? = null,
        @get:NotNull val lastName: String? = null,
        @get:NotNull val address: String? = null,
        val city: String? = null,
        @get:NotNull @get:Size(min = 9, max = 13) val telephone: String? = null
    )

    class OwnerPutRequest(
        val firstName: String? = null,
        val lastName: String? = null,
        val address: String? = null,
        val city: String? = null,
        @get:Size(min = 9, max = 13) val telephone: String? = null
    )

}