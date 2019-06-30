package com.terra

import com.terra.placeApi.amadeus.AmadeusPlaceApi
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class TerraApplication

fun main(args: Array<String>) {
	//SpringApplication.run(TerraApplication::class.java, *args)


	val api = AmadeusPlaceApi()

	api.place(41.397158, 2.160873, 1)
}
