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
)