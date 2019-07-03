package com.terra.repository

import com.terra.model.Place
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface PlaceRepository : MongoRepository<Place, String> {

    override fun findById(@Param("id") id: String): Optional<Place>

    fun findAllByLatitudeBetweenAndLongitudeBetween(latitudeGT: Double, latitudeLT: Double, longitudeGT: Double, longitudeLT: Double): List<Place>
}