package com.terra.apis.placeApi

import com.terra.model.Place

interface PlaceApi {

    fun places(lat: Double, lng: Double, radius: Int): List<Place>

    fun places(lat: Double, lng: Double, radius: Int, pageLimit: Int, pageOffset: Int): List<Place>
}