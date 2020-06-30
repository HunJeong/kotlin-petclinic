package org.hooney.petclinic.test_utils

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.mock.web.MockHttpServletResponse

fun <T> MockHttpServletResponse.contentAsT(clazz: Class<T>) = ObjectMapper().readValue(contentAsString, clazz) ?: throw RuntimeException()