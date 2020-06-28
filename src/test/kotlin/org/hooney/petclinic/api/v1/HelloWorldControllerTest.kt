package org.hooney.petclinic.api.v1

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(HelloWorldController::class)
class HelloWorldControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun helloWorld() {
        mockMvc.perform(get("/api/v1/hello_world"))
            .andExpect(status().isOk)
            .andExpect(content().string("Hello World"))
    }
}