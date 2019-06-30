package com.terra.placeApi

interface PlaceApi {

    fun place(lat: Double, lng: Double, radius: Int): Unit//todo place model

    fun place(lat: Double, lng: Double, radius: Int, pageLimit: Int, pageOffset: Int): Unit//todo place model
}