package com.terra.apis.placeApi.amadeus.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.terra.apis.placeApi.ApiResponseError


class AmadeusPoisError {

    @SerializedName("errors")
    @Expose
    var errors: List<Error>? = null


    fun toAmadeusApiError(): ApiResponseError {
        return if (errors == null || errors!!.isEmpty()) {
            ApiResponseError(666, "Unknown error")
        } else {
            ApiResponseError(
                    errors!!.first().code!!.toInt(),
                    errors!!.first().detail!!
            )
        }
    }
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