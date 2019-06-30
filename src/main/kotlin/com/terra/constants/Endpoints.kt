package com.terra.constants

interface Endpoints {
    /**
     * API calls:
     */

    /**
     * V1
     */
    val API_V1: String
        get() = "/api/v1"

    val V1_FIND_USER_BY_TOKEN: String
        get() = "$API_V1/userByToken/{token}"

    val V1_PLACES: String
        get() = "$API_V1/cities/{name}/places"

}

