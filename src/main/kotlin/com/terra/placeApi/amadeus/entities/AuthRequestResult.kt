package com.terra.placeApi.amadeus.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthRequestResult {

    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("username")
    @Expose
    var username: String? = null
    @SerializedName("application_name")
    @Expose
    var applicationName: String? = null
    @SerializedName("client_id")
    @Expose
    var clientId: String? = null
    @SerializedName("token_type")
    @Expose
    var tokenType: String? = null
    @SerializedName("access_token")
    @Expose
    var accessToken: String? = null
    @SerializedName("expires_in")
    @Expose
    var expiresIn: Int? = null
    @SerializedName("state")
    @Expose
    var state: String? = null
    @SerializedName("scope")
    @Expose
    var scope: String? = null

}