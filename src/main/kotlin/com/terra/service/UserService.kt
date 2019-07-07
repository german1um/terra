package com.terra.service

import com.terra.dto.UserDto
import com.terra.model.Token
import com.terra.model.User
import com.terra.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired val userRepository: UserRepository) {

    fun login(token: Token): UserDto {
        val user = getOrSaveByToken(token)
        return UserDto(user.id, user.info)
    }

    fun getOrSaveByToken(token: Token): User {
        return userRepository.findByTokensIn(listOf(token)) ?: userRepository.save(User(token))
    }

    fun getById(id: String): User {
        return userRepository.findById(id).get()
    }

    fun markPlaceAsSeen(userId: String, placeId: String) {
        val user = getById(userId)
        user.seenPlaces.add(placeId)
        userRepository.save(user)
    }

}