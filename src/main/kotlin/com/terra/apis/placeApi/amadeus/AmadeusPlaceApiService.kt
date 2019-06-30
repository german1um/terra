package com.terra.apis.placeApi.amadeus

import com.terra.apis.placeApi.amadeus.entities.AuthRequestResult
import com.terra.apis.placeApi.amadeus.entities.PlaceRequestResult
import retrofit2.Call
import retrofit2.http.*

interface AmadeusPlaceApiService {


    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("security/oauth2/token")
    fun auth(
            @Field("grant_type") grandType: String = "client_credentials",
            @Field("client_id") clientId: String,
            @Field("client_secret") clientSecret: String
    ): Call<AuthRequestResult>

    @GET("reference-data/locations/pois")
    fun placeByLocationAndRadius(
            @Header("Authorization") auth: String,
            @Query("latitude") latitude: Double,
            @Query("longitude") longitude: Double,
            @Query("radius") radius: Int,
            @Query("page[limit]") pageLimit: Int = 1,
            @Query("page[offset]") pageOffset: Int = 0
    ): Call<PlaceRequestResult>

    @GET("reference-data/locations/pois/by-square")
    fun placeBySquareCoords(
            @Header("Authorization") auth: String,
            @Query("north") north: Double,
            @Query("west") west: Double,
            @Query("south") south: Double,
            @Query("east") east: Double,
            @Query("page[limit]") pageLimit: Int = 1,
            @Query("page[offset]") pageOffset: Int = 0
    ): Call<PlaceRequestResult>

}