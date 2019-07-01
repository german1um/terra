package com.terra.dto

class PlaceDto(
        val name: String,
        val description: String = "placeholder",
        val timesVisited: Int = -1,
        val lng: Double,
        val lat: Double

)

