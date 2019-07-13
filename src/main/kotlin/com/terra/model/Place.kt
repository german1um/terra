package com.terra.model

import com.terra.model.PlaceType.HISTORY
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
class Place (
        val id: String = UUID.randomUUID().toString(),
        val name: String = "",
        val description: String = "",
        var timesVisited: Int = 0,

        val lng: Double = 0.0,
        val lat: Double = 0.0,
        val type: PlaceType = HISTORY,

        val provider: PlaceProvider = PlaceProvider.TERRA,
        val hash: String = "$name$lng$lat"
)

enum class PlaceProvider(val value: Int) {
    TERRA(0),
    AMADEUS(1)
}

fun intToPlaceProvider(i: Int): PlaceProvider {
    return PlaceProvider.values().first { it.value == i }
}

enum class PlaceType(val value: Int) {
    UNDERGRAUND(0),
    FOOD(1),
    HISTORY(2),
    NATURE(3)
}

fun intToPlaceCategory(i: Int): PlaceType {
    return PlaceType.values().first { it.value == i }
}