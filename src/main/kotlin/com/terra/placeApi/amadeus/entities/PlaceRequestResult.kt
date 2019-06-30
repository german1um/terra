package com.terra.placeApi.amadeus.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PlaceRequestResult {

    @SerializedName("data")
    @Expose
    var data: List<PlaceRequestData>? = null
    @SerializedName("meta")
    @Expose
    var meta: PlaceRequestMeta? = null

}