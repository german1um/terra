package com.terra.model

import com.terra.model.PlaceType.HISTORY
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import kotlin.collections.HashMap

@Document
class Place (
        val id: String = UUID.randomUUID().toString(),
        val name: String = "",
        val description: String = "",
        var timesVisited: Int = 0,

        val lng: Double = 0.0,
        val lat: Double = 0.0,
        val type: PlaceType = HISTORY,

        val rating: PlaceRating = PlaceRating(),

        val provider: PlaceProvider = PlaceProvider.TERRA,
        val hash: String = "$name$lng$lat"
) {

    fun addRating(userId: String, userRating: Int): Double {
        if (!rating.userRatings.containsKey(userId)) {
            rating.average = ((rating.average * rating.userRatings.size) + userRating)/(rating.userRatings.size + 1)
            rating.userRatings[userId] = userRating
        }

        return rating.average
    }
}

enum class PlaceProvider(val value: Int) {
    TERRA(0),
    AMADEUS(1)
}

enum class PlaceType(val value: Int) {
    UNDERGRAUND(0),
    FOOD(1),
    HISTORY(2),
    NATURE(3)
}


/**
 * @average - average rating of place
 * @userRatings - map of ratings by users: <userId, userRating>
 */
data class PlaceRating(
        var average: Double = 0.0,
        val userRatings: HashMap<String, Int> = hashMapOf()
)

fun intToPlaceProvider(i: Int): PlaceProvider {
    return PlaceProvider.values().first { it.value == i }
}

fun intToPlaceCategory(i: Int): PlaceType {
    return PlaceType.values().first { it.value == i }
}
