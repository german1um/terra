package com.terra.model

import com.terra.model.PlaceCategory.DEFAULT
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
class Place (
        val id: String = UUID.randomUUID().toString(),
        val name: String = "",
        val description: String = "",
        var timesVisited: Int = 0,
        val longitude: Double = 0.0,
        val latitude: Double = 0.0,
        val category: PlaceCategory = DEFAULT,

        val provider: PlaceProvider = PlaceProvider.TERRA,
        val hash: String = "$name$longitude$latitude"
)

enum class PlaceProvider(val value: Int) {
    TERRA(0),
    AMADEUS(1)
}

fun intToPlaceProvider(i: Int): PlaceProvider {
    return PlaceProvider.values().first { it.value == i }
}

enum class PlaceCategory(val value: Int) {
    DEFAULT(0),
    ARCHITECTURE(1),
    FOOD(2),
    FANCY(3)
}

fun intToPlaceCategory(i: Int): PlaceCategory {
    return PlaceCategory.values().first { it.value == i }
}