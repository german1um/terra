package com.terra.model

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
class Place (
        val id: String = UUID.randomUUID().toString(),
        val name: String = "",
        val description: String = "",
        val timesVisited: Int = 0,
        val longitude: Double = 0.0,
        val latitude: Double = 0.0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Place

        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (timesVisited != other.timesVisited) return false
        if (longitude != other.longitude) return false
        if (latitude != other.latitude) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + timesVisited
        result = 31 * result + longitude.hashCode()
        result = 31 * result + latitude.hashCode()
        return result
    }
}

enum class PlaceProvider(val value: Int) {
    TERRA(0),
    AMADEUS(1)
}