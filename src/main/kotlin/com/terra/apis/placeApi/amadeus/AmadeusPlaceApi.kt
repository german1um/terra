package com.terra.apis.placeApi.amadeus

import com.google.gson.Gson
import com.terra.apis.placeApi.ApiResponse
import com.terra.apis.placeApi.PlaceApi
import com.terra.apis.placeApi.amadeus.entities.AmadeusApiError
import com.terra.apis.placeApi.amadeus.entities.AmadeusPoisError
import com.terra.apis.placeApi.amadeus.entities.PlaceRequestResult
import com.terra.model.Place
import com.terra.model.PlaceProvider
import com.terra.repository.PlaceRepository
import okhttp3.ResponseBody
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

        //auth()
    }

    override fun places(lat: Double, lng: Double, radius: Int): AmadeusApiResponse {
        val apiResponse = sendPlaceRequest(lat, lng, radius)

        return if (apiResponse.places == null && apiResponse.error!!.code == 38192) {
            auth()

            sendPlaceRequest(lat, lng, radius)
        } else {
            apiResponse
        }
    }

    override fun places(lat: Double, lng: Double, radius: Int, pageLimit: Int, pageOffset: Int): AmadeusApiResponse {
        val apiResponse = sendPlaceRequest(lat, lng, radius, pageLimit, pageOffset)

        return if (apiResponse.places == null && apiResponse.error!!.code == 38192) {
            auth()

            sendPlaceRequest(lat, lng, radius, pageLimit, pageOffset)
        } else {
            apiResponse
        }
    }

    fun sendPlaceRequest(lat: Double, lng: Double, radius: Int): AmadeusApiResponse {
        val response = api.placeByLocationAndRadius(
                auth = "Bearer ${config.token}",
                lat = lat,
                lng = lng,
                radius = radius
        ).execute()

        val responseBody = response.body() ?: return extractError(response.errorBody()!!)

        return AmadeusApiResponse(places = matchAmadeusPlacesToOur(responseBody))
    }

    fun sendPlaceRequest(lat: Double, lng: Double, radius: Int, pageLimit: Int, pageOffset: Int): AmadeusApiResponse {
        val response = api.placeByLocationAndRadius(
                auth = "Bearer ${config.token}",
                lat = lat,
                lng = lng,
                radius = radius,
                pageLimit = pageLimit,
                pageOffset = pageOffset
        ).execute()


        val responseBody = response.body() ?: return extractError(response.errorBody()!!)

        return AmadeusApiResponse(places = matchAmadeusPlacesToOur(responseBody))
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

    private fun matchAmadeusPlacesToOur(response: PlaceRequestResult): List<Place> {
        return response.data?.map { data ->

            val hash = "${data.name}${data.geoCode?.lng ?: 0.0}${data.geoCode?.lat ?: 0.0}"

            if (!placeRepository.findByHash(hash).isPresent) {
                placeRepository.save(
                        Place(
                                name = data.name ?: "",
                                description = data.tags?.joinToString { str1 -> " #$str1" } ?: "",
                                lng = data.geoCode?.lng ?: 0.0,
                                lat = data.geoCode?.lat ?: 0.0,
                                provider = PlaceProvider.AMADEUS,
                                type = data.matchCategory()
                        ))
            }

            placeRepository.findByHash(hash).get()
        } ?: emptyList()
    }

    private fun mathAmadeusPoisErrorToOur(amadeusError: AmadeusPoisError): AmadeusApiError {
        return if (amadeusError.errors == null || amadeusError.errors!!.isEmpty()) {
            AmadeusApiError(666, "Unknown error")
        } else {
            AmadeusApiError(
                    amadeusError.errors!!.first().code!!.toInt(),
                    amadeusError.errors!!.first().detail!!
            )
        }
    }

    private fun extractError(error: ResponseBody): AmadeusApiResponse {
        val errorString = error.string()
        val amadeusPoisError = Gson().fromJson(errorString, AmadeusPoisError::class.java)

        return AmadeusApiResponse(error = mathAmadeusPoisErrorToOur(amadeusPoisError))
    }
}

data class AmadeusApiResponse (
        override val places: List<Place>? = null,

        val error: AmadeusApiError? = null
) : ApiResponse