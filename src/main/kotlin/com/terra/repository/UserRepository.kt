package com.terra.repository

import com.terra.model.Token
import com.terra.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface UserRepository : MongoRepository<User, String> {

    fun findByTokensIn(@Param("tokens") tokens: List<Token>): User?

    override fun findById(@Param("id") id: String): Optional<User>

}