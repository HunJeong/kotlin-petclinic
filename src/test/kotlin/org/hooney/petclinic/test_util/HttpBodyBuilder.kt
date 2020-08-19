package org.hooney.petclinic.test_util

import org.json.JSONObject

class HttpBodyBuilder(vararg pairs: Pair<String, Any?>) {

    private val pairs = pairs

    fun build() = JSONObject(hashMapOf(*pairs)).toString()
}