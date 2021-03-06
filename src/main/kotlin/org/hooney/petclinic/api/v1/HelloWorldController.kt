package org.hooney.petclinic.api.v1

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {

    @GetMapping("/api/v1/hello_world")
    fun helloWorld(): ResponseEntity<String> = ResponseEntity.ok("Hello World")

}