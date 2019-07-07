package com.terra.controller

import com.terra.controller.domain.PlaceResponse
import com.terra.dto.HiddenPlaceDto
import com.terra.dto.OpenPlaceDto
import com.terra.model.*
import com.terra.service.PlaceService
import com.terra.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PlaceController(
        @Autowired val placeService: PlaceService,
        @Autowired val userService: UserService
) {

    @GetMapping("/places/getInRadius")
    fun getInRadius(lat: Double, lng: Double, radius: Int, provider: Int, userId: String): PlaceResponse {
        val places = placeService.getPlacesInRadius(lat, lng, radius, intToPlaceProvider(provider)).toMutableList()

        return getPlaceResponse(places, userId)
    }

    @GetMapping("/places")
    fun get(provider: Int, userId: String): PlaceResponse {
        val places = placeService.getAllPlaces().toMutableList()

        return getPlaceResponse(places, userId)
    }

    @PostMapping("/places")
    fun save(hiddenPlaceDto: HiddenPlaceDto) {
        placeService.save(hiddenPlaceDto)
    }

    private fun getPlaceResponse(places: MutableList<Place>, userId: String): PlaceResponse {
        val userPlaces = userService.getById(userId).seenPlaces
        val openPlaces = mutableListOf<OpenPlaceDto>()
        userPlaces.forEach { userPlaceId ->
            val place = places.find { it.id == userPlaceId }

            if (place != null) {
                places.remove(place)
                openPlaces.add(OpenPlaceDto(place))
            }
        }

        val hiddenPlaces = places.map { place ->
            HiddenPlaceDto(place)
        }

        return PlaceResponse(hiddenPlaces, openPlaces)
    }

}