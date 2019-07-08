package com.terra.controller

import com.terra.dto.PlaceDto
import com.terra.model.intToPlaceProvider
import com.terra.service.PlaceService
import com.terra.service.UserActionService
import com.terra.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PlaceController(
        @Autowired val placeService: PlaceService,
        @Autowired val userService: UserService,
        @Autowired val userActionService: UserActionService
) {

    @GetMapping("/places/getInRadius")
    fun getInRadius(lat: Double, lng: Double, radius: Int, provider: Int, userId: String): List<PlaceDto> {
        return userActionService.getPlacesInRadiusForUser(lat, lng, radius, intToPlaceProvider(provider), userId)
    }

    @GetMapping("/places")
    fun get(provider: Int, userId: String): List<PlaceDto> {
        return userActionService.getAllPlacesForUser(userId)
    }

    @PostMapping("/places")
    fun save(placeDto: PlaceDto) {
        placeService.save(placeDto)
    }
}