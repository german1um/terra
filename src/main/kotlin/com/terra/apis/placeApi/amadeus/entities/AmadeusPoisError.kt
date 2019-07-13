package com.terra.apis.placeApi.amadeus.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class AmadeusPoisError {

    @SerializedName("errors")
    @Expose
    var errors: List<Error>? = null

}

class Error {

    @SerializedName("code")
    @Expose
    var code: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("detail")
    @Expose
    var detail: String? = null
    @SerializedName("status")
    @Expose
    var status: String? = null

}