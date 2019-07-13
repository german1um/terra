package com.terra.apis.placeApi

import com.terra.model.Place

interface PlaceApi {

    fun places(lat: Double, lng: Double, radius: Int): ApiResponse

    fun places(lat: Double, lng: Double, radius: Int, pageLimit: Int, pageOffset: Int): ApiResponse
}

interface ApiResponse {
    val places: List<Place>?
}