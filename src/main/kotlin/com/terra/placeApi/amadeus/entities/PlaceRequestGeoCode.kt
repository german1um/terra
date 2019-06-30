package com.terra.placeApi.amadeus.entities


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PlaceRequestGeoCode {

    @SerializedName("latitude")
    @Expose
    var latitude: Double? = null
    @SerializedName("longitude")
    @Expose
    var longitude: Double? = null

}