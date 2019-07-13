package com.terra.apis.placeApi.amadeus.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AmadeusAuthError {

    @SerializedName("error")
    @Expose
    var error: String? = null
    @SerializedName("error_description")
    @Expose
    var errorDescription: String? = null
    @SerializedName("code")
    @Expose
    var code: Int? = null
    @SerializedName("title")
    @Expose
    var title: String? = null

}