package com.terra.controller

import com.terra.controller.domain.PlaceResponse
import com.terra.dto.PlaceDto
import com.terra.model.intToPlaceProvider
import com.terra.service.PlaceService
import com.terra.service.UserActionService
import com.terra.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PlaceController(
        @Autowired val placeService: PlaceService,
        @Autowired val userService: UserService,
        @Autowired val userActionService: UserActionService
) {

    @GetMapping("/places/getInRadius")
    fun getInRadius(lat: Double, lng: Double, radius: Int, provider: Int, userId: String): PlaceResponse {
        val places = placeService.getPlacesInRadius(lat, lng, radius, intToPlaceProvider(provider)).toMutableList()

        return userService.getPlaceResponse(places, userId)
    }

    @GetMapping("/places")
    fun get(provider: Int, userId: String): List<PlaceDto> {
        return userActionService.getAllPlacesForUser(userId)
    }

    @PostMapping("/places")
    fun save(placeDto: PlaceDto) {
        placeService.save(placeDto)
    }

    @PostMapping("/places/{placeId}/increase-view-count")
    private fun increateViewCount(@PathVariable placeId: String, userId: String) {

        if (!userService.isPlaceSeenByUser(placeId, userId)) {
            val place = placeService.getPlaceById(placeId)
            place.timesVisited++
            placeService.save(place)
        }
    }

    private fun getPlaceResponse(places: MutableList<Place>, userId: String): PlaceResponse {
        val userPlaces = userService.getById(userId).seenPlaces
        val openPlaces = mutableListOf<OpenPlaceDto>()
        userPlaces.forEach { userPlaceId ->
            val place = places.find { it.id == userPlaceId }

//    @GetMapping("/places")
//    fun get(provider: Int, userId: String): PlaceResponse {
//        val places = placeService.getAllPlaces().toMutableList()
//
//        return userService.getPlaceResponse(places, userId)
//    }
//
//    @PostMapping("/places")
//    fun save(hiddenPlaceDto: HiddenPlaceDto) {
//        placeService.save(hiddenPlaceDto)
//    }

        val hiddenPlaces = places.map { place ->
            HiddenPlaceDto(place)
        }

        return PlaceResponse(hiddenPlaces, openPlaces)
    }

}