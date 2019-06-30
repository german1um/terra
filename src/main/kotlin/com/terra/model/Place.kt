package com.terra.model

import org.geojson.geometry.Point
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
class Place (
        val id: String = UUID.randomUUID().toString(),
        val name: String = "",
        val cityName: String = "",
        val description: String = "",
        val timesVisited: Int = 0,
        val coordinates: Point = Point(0.0,0.0)
)