package com.terra.repository

import com.terra.model.Place
import com.terra.model.PlaceProvider
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface PlaceRepository : MongoRepository<Place, String> {

    override fun findById(@Param("id") id: String): Optional<Place>

    fun findByHash(@Param("hash") hash: String): Optional<Place>

    fun findByProvider(@Param("provider") provider: PlaceProvider): List<Place>

    fun findAllByLatBetweenAndLngBetween(latitudeGT: Double, latitudeLT: Double, longitudeGT: Double, longitudeLT: Double): List<Place>

}