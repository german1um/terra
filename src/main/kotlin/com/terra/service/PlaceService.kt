package com.terra.service

import com.terra.apis.placeApi.amadeus.AmadeusPlaceApi
import com.terra.dto.PlaceDto
import com.terra.model.Place
import com.terra.model.PlaceProvider
import com.terra.model.PlaceProvider.AMADEUS
import com.terra.model.PlaceProvider.TERRA
import com.terra.model.intToPlaceCategory
import com.terra.repository.PlaceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class PlaceService(@Autowired val placeRepository: PlaceRepository, @Autowired val amadeusApi: AmadeusPlaceApi) {

    fun getPlacesInRadius(lat: Double, lng: Double, radius: Int, provider: PlaceProvider): List<Place> {
        return when (provider) {
            TERRA -> getMongoPlacesInRadius(lat, lng, radius)
            AMADEUS -> getAmadeusPlacesInRadius(lat, lng, radius)
        }
    }

    private fun getMongoPlacesInRadius(lat: Double, lng: Double, radius: Int): List<Place> {
        return placeRepository.findAllByLatBetweenAndLngBetween(
                lat - radius,
                lat + radius,
                lng - radius,
                lng + radius
        ).filter { it.provider != AMADEUS }
    }

    private fun getAmadeusPlacesInRadius(lat: Double, lng: Double, radius: Int): List<Place> {
        return amadeusApi.places(lat, lng, radius)
    }

    fun getAllPlacesInRadius(lat: Double, lng: Double, radius: Int): List<Place> {
        val places = mutableListOf<Place>()

        places.addAll(getMongoPlacesInRadius(lat, lng, radius))

        places.addAll(getAmadeusPlacesInRadius(lat, lng, radius))

        return places
    }



    fun getPlaceByProvider(provider: PlaceProvider): List<Place> {
        return placeRepository.findByProvider(provider)
    }

    fun save(placeDto: PlaceDto) {
        placeRepository.save(
                Place(
                        name = placeDto.name,
                        description = placeDto.description,
                        timesVisited = placeDto.timesVisited,
                        lat = placeDto.lat,
                        lng = placeDto.lng,
                        type = intToPlaceCategory(placeDto.category)
                )
        )
    }

    fun save(place: Place) {
        placeRepository.save(place)
    }

    fun delete(id: String) {
        placeRepository.findById(id).ifPresent {
            placeRepository.delete(it)
        }
    }

    fun getAllPlaces(): List<Place> {
        return placeRepository.findAll()
    }

    fun getById(id: String): Place {
        return placeRepository.findById(id).orElseThrow {
            ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Place Not Found")
        }
    }

    fun getPlaceRating(placeId: String): Double {
        return getById(placeId).rating.average
    }
  
    fun isExistant(id: String): Boolean {
        return placeRepository.existsById(id)
    }

    fun getPlaceRating(placeId: String): Double {
        return getById(placeId).rating.average
    }
}