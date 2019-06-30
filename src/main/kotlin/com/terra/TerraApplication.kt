package com.terra

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class TerraApplication

fun main(args: Array<String>) {
	SpringApplication.run(TerraApplication::class.java, *args)
}
