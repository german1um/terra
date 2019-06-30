package com.terra.apis.placeApi.amadeus.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PlaceRequestMeta {

    @SerializedName("count")
    @Expose
    var count: Int? = null
    @SerializedName("links")
    @Expose
    var links: PlaceRequestLinks? = null

}