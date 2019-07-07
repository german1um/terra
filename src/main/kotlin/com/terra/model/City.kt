package com.terra.model

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
class City {
    val id: String = UUID.randomUUID().toString()
    val name: String = "default name"
    val placesIDS: MutableList<String> = mutableListOf()
}