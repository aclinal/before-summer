package ca.alexleung.springkoefficient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class SpringKoefficientApplication

@RestController
@RequestMapping("/api")
class ApiController {
  @GetMapping("/greeting")
  fun getGreeting(): String {
    return "Hello world from API."
  }
}

fun main(args: Array<String>) {
  runApplication<SpringKoefficientApplication>(*args)
}
