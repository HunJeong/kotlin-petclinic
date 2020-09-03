package org.hooney.petclinic.test_util

import org.json.JSONObject

class HttpBodyBuilder(pairs: MutableMap<String, *>) {

    constructor(vararg pairs: Pair<String, Any?>): this(pairs.toMap().toMutableMap())

    private var pairs = pairs

    fun build() = JSONObject(pairs).toString()
}