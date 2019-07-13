package com.terra.service

import com.terra.dto.PlaceDto
import com.terra.model.Place
import com.terra.model.PlaceProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserActionService(@Autowired val userService: UserService, @Autowired val placeService: PlaceService) {
    fun getAllPlacesForUser(id: String): List<PlaceDto> {
        val user = userService.getById(id)
        val allPlaces = placeService.getAllPlaces()

        return allPlaces.map { PlaceDto(it, user) }
    }

    fun markPlaceAsSeen(userId: String, placeId: String): Boolean {
        val user = userService.getById(userId)
        if (!placeService.isExistant(placeId)) {
            throw ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Place Not Found")
        }

        return if (!user.isVisited(placeId)) {
            user.visitedPlaces.add(placeId)
            userService.save(user)
            val place = placeService.getPlaceById(placeId)
            place.timesVisited++
            placeService.save(place)
            true
        } else {
            false
        }
    }

    fun getPlacesInRadiusForUser(lat: Double,
                                 lng: Double,
                                 radius: Int,
                                 provider: PlaceProvider,
                                 userId: String): List<PlaceDto> {
        return markVisitedPlaces(
                placeService.getAllPlacesInRadius(lat, lng, radius),
                userId
        )
    }

    private fun markVisitedPlaces(places: List<Place>, id: String): List<PlaceDto> {
        val user = userService.getById(id)
        return places.map { PlaceDto(it, user) }
    }

    //todo check, then place is exist by normal method
    fun isPlaceVisitedByUser(placeId: String, userId: String): Boolean {
        placeService.getById(placeId)
        val user = userService.getById(userId)

        return user.visitedPlaces.contains(placeId)
    }

    fun addPlaceRating(placeId: String, userId: String, userRating: Int): Double {
        userService.getById(userId)
        val place = placeService.getById(placeId)

        place.addRating(userId, userRating)
        placeService.save(place)

        return place.rating.average
    }
}