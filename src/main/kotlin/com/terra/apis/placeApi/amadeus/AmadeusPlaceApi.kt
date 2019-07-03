package com.terra.apis.placeApi.amadeus

import com.terra.apis.placeApi.PlaceApi
import com.terra.apis.placeApi.amadeus.entities.PlaceRequestResult
import com.terra.model.Place
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AmadeusPlaceApi : PlaceApi {

    private val config = AmadeusConfig()

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://test.api.amadeus.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val api: AmadeusPlaceApiService


    init {
        api = retrofit.create(AmadeusPlaceApiService::class.java)

        val response = api.auth(
                clientId = config.clientId,
                clientSecret = config.clientSecret
        ).execute()


        if (response.body() != null) {
            config.token = response.body()?.accessToken ?: ""
        }
    }

    override fun places(lat: Double, lng: Double, radius: Int): List<Place> {
        val response = api.placeByLocationAndRadius(
                auth = "Bearer ${config.token}",
                latitude = lat,
                longitude = lng,
                radius = radius
        ).execute().body() ?: return emptyList()

        return matchAmadeusPlacesToOur(response)
    }

    override fun places(lat: Double, lng: Double, radius: Int, pageLimit: Int, pageOffset: Int): List<Place> {
        val response = api.placeByLocationAndRadius(
                auth = "Bearer ${config.token}",
                latitude = lat,
                longitude = lng,
                radius = radius,
                pageLimit = pageLimit,
                pageOffset = pageOffset
        ).execute().body() ?: return emptyList()

        return matchAmadeusPlacesToOur(response)
    }

    private fun matchAmadeusPlacesToOur(response: PlaceRequestResult): List<Place> {
        return response.data?.map { data ->
            Place(
                    name = data.name ?: "",
                    description = data.tags?.joinToString { str1 -> " #$str1" } ?: "",
                    longitude = data.geoCode?.longitude ?: 0.0,
                    latitude = data.geoCode?.latitude ?: 0.0
            )
        } ?: emptyList()
    }
}