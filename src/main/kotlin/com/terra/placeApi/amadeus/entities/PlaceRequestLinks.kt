package com.terra.placeApi.amadeus.entities


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PlaceRequestLinks {

    @SerializedName("self")
    @Expose
    var self: String? = null
    @SerializedName("next")
    @Expose
    var next: String? = null

}