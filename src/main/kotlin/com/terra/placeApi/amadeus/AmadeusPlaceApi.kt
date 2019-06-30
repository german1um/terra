package com.terra.placeApi.amadeus

import com.terra.placeApi.PlaceApi
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

    override fun place(lat: Double, lng: Double, radius: Int) {
        val response = api.placeByLocationAndRadius(
                auth = "Bearer ${config.token}",
                latitude = lat,
                longitude = lng,
                radius = radius
        ).execute()
    }

    override fun place(lat: Double, lng: Double, radius: Int, pageLimit: Int, pageOffset: Int) {

    }
}