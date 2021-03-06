package com.terra.model

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
class User(
        val id: String,
        val tokens: MutableList<Token>,
        val info: UserInfo,
        val visitedPlaces: MutableList<String> = mutableListOf()
) {
    fun isVisited(place: Place): Boolean {
        return visitedPlaces.contains(place.id)
    }

    fun isVisited(placeId: String): Boolean {
        return visitedPlaces.contains(placeId)
    }
    constructor(token: Token) : this(UUID.randomUUID().toString(), mutableListOf(token), UserInfo())
}

class Token(
        val token: String,
        val type: TokenType
) {
    constructor(
            token: String,
            type: Int
    ) : this (
            token,
            TokenType.values().find { it.value == type } ?: TokenType.ODNOKLASSNIKI
    )
}


enum class TokenType(val value: Int) {
    GOOGLE(0),
    APPLE(1),
    VK(2),
    FACEBOOK(3),
    ODNOKLASSNIKI(4)
}

