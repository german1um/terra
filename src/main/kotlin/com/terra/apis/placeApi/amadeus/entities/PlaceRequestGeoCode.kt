package com.terra.apis.placeApi.amadeus.entities


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PlaceRequestGeoCode {

    @SerializedName("latitude")
    @Expose
    var lat: Double? = null
    @SerializedName("longitude")
    @Expose
    var lng: Double? = null

}