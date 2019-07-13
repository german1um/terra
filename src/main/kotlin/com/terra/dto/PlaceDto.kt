package com.terra.dto

import com.terra.model.Place
import com.terra.model.PlaceType
import com.terra.model.User
import java.util.*

class PlaceDto(
        val id: String = UUID.randomUUID().toString(),
        val name: String = "",
        val description: String = "",
        val timesVisited: Int = 0,
        val lng: Double = 0.0,
        val lat: Double = 0.0,
        val category: Int = PlaceType.HISTORY.value,
        val visited: Boolean = false,
        val averageRating: Double = 0.0,
        val userRate: Int = 0

) {
    constructor(place: Place, user: User) : this(
            id = place.id,
            name = place.name,
            description = place.description,
            timesVisited = place.timesVisited,
            lng = place.lng,
            lat = place.lat,
            category = place.type.value,
            visited = user.isVisited(place),
            averageRating = place.rating.average,
            userRate = place.rating.userRatings[user.id] ?: 0
    )

}
