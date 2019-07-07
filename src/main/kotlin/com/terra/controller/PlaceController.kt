package com.terra.controller

import com.terra.dto.PlaceDto
import com.terra.service.PlaceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PlaceController(@Autowired val placeService: PlaceService) {


    @GetMapping("/places/getInRadius")
    fun getInRadius(lat: Double, lng: Double, radius: Int, provider: Int): List<PlaceDto> {
        return placeService.getPlacesInRadius(lat, lng, radius, provider)
    }

    @GetMapping("/places")
    fun get(provider: Int): List<PlaceDto> {
        return placeService.getAllPlaces().map { PlaceDto(it) }
    }

    @PostMapping("/places")
    fun save(placeDto: PlaceDto) {
        placeService.save(placeDto)
    }
}