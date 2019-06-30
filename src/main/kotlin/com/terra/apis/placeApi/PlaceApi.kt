package com.terra.apis.placeApi

interface PlaceApi {

    fun places(lat: Double, lng: Double, radius: Int): Unit//todo place model

    fun places(lat: Double, lng: Double, radius: Int, pageLimit: Int, pageOffset: Int): Unit//todo place model
}