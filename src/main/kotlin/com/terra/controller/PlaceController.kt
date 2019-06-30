package com.terra.controller

import com.terra.apis.placeApi.amadeus.AmadeusPlaceApi
import com.terra.model.Place
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PlaceController {

    private val amadeusApi = AmadeusPlaceApi()

    @GetMapping("/places")
    fun places(lat: Double, lng: Double, radius: Int): List<Place> {
        return amadeusApi.places(lat, lng, radius)
    }
}