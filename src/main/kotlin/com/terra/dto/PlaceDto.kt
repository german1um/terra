package com.terra.dto

import com.terra.model.Place
import com.terra.model.PlaceCategory.DEFAULT
import com.terra.model.User

class PlaceDto(
        val id: String,
        val name: String = "",
        val description: String = "",
        val timesVisited: Int = 0,
        val lng: Double = 0.0,
        val lat: Double = 0.0,
        val category: Int = DEFAULT.value,
        val visited: Boolean = false

) {
    constructor(place: Place, user: User) : this(
            id = place.id,
            name = place.name,
            description = place.description,
            timesVisited = place.timesVisited,
            lng = place.longitude,
            lat = place.latitude,
            category = place.category.value,
            visited = user.visited(place)
    )

}
