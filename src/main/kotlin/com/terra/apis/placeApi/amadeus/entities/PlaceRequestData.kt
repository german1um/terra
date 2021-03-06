package com.terra.apis.placeApi.amadeus.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.terra.model.PlaceType
import com.terra.model.PlaceType.*

class PlaceRequestData {

    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("subType")
    @Expose
    var subType: String? = null
    @SerializedName("geoCode")
    @Expose
    var geoCode: PlaceRequestGeoCode? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("category")
    @Expose
    var category: String? = null
    @SerializedName("tags")
    @Expose
    var tags: List<String>? = null


    fun matchCategory(): PlaceType {
        println(category)
        return when (category) {
            "RESTAURANT" -> FOOD
            "SIGHTS" -> HISTORY
            else -> HISTORY
        }
    }
}