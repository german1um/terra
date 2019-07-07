package com.terra.apis.placeApi.amadeus

import com.terra.apis.placeApi.PlaceApi
import com.terra.apis.placeApi.amadeus.entities.PlaceRequestData
import com.terra.apis.placeApi.amadeus.entities.PlaceRequestResult
import com.terra.model.Place
import com.terra.model.PlaceProvider
import com.terra.repository.PlaceRepository
import com.terra.service.PlaceService
import org.geojson.geometry.Point
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Component
class AmadeusPlaceApi(
        @Autowired val placeRepository: PlaceRepository
) : PlaceApi {

    private val config = AmadeusConfig()

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://test.api.amadeus.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val api: AmadeusPlaceApiService


    init {
        api = retrofit.create(AmadeusPlaceApiService::class.java)

        auth()
    }

    override fun places(lat: Double, lng: Double, radius: Int): List<Place> {
        val response = api.placeByLocationAndRadius(
                auth = "Bearer ${config.token}",
                latitude = lat,
                longitude = lng,
                radius = radius
        ).execute().body()

        return if (response == null) {
            auth()
            places(lat, lng, radius)
        } else {
            matchAmadeusPlacesToOur(response)
        }
    }

    override fun places(lat: Double, lng: Double, radius: Int, pageLimit: Int, pageOffset: Int): List<Place> {
        val response = api.placeByLocationAndRadius(
                auth = "Bearer ${config.token}",
                latitude = lat,
                longitude = lng,
                radius = radius,
                pageLimit = pageLimit,
                pageOffset = pageOffset
        ).execute().body()

        return if (response == null) {
            auth()
            places(lat, lng, radius, pageLimit, pageOffset)
        } else {
            matchAmadeusPlacesToOur(response)
        }
    }

    private fun matchAmadeusPlacesToOur(response: PlaceRequestResult): List<Place> {
        return response.data?.map { data ->

            val hash = "${data.name}${data.geoCode?.longitude ?: 0.0}${data.geoCode?.latitude ?: 0.0}"

            if (!placeRepository.findByHash(hash).isPresent) {
                placeRepository.save(
                        Place(
                                name = data.name ?: "",
                                description = data.tags?.joinToString { str1 -> " #$str1" } ?: "",
                                longitude = data.geoCode?.longitude ?: 0.0,
                                latitude = data.geoCode?.latitude ?: 0.0,
                                provider = PlaceProvider.AMADEUS,
                                category = data.matchCategory()
                        ))
            }

            placeRepository.findByHash(hash).get()
        } ?: emptyList()
    }

    private fun auth() {
        val response = api.auth(
                clientId = config.clientId,
                clientSecret = config.clientSecret
        ).execute()


        if (response.body() != null) {
            config.token = response.body()?.accessToken ?: ""
        }
    }
}