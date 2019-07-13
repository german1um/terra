package com.terra.dto

import com.terra.model.Place
import com.terra.model.PlaceType.HISTORY

class OpenPlaceDto(
        val id: String = "",
        val name: String,
        val description: String = "placeholder",
        val timesVisited: Int = -1,
        val lng: Double,
        val lat: Double,
        val category: Int = HISTORY.value
) {
    constructor(place: Place) : this(
            place.id,
            place.name,
            place.description,
            place.timesVisited,
            place.lng,
            place.lat,
            place.type.value
    )
}