package com.terra.service

import com.terra.dto.UserDto
import com.terra.model.Token
import com.terra.model.User
import com.terra.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(@Autowired val userRepository: UserRepository) {

    fun login(token: Token): UserDto {
        val user = getOrSaveByToken(token)
        return UserDto(user.id)
    }

    fun getOrSaveByToken(token: Token): User {
        return userRepository.findByTokensIn(listOf(token)) ?: userRepository.save(User(token))
    }

    fun getById(id: String): User {
        return userRepository.findById(id).orElseThrow {
            ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Not Found")
        }
    }

    fun isPlaceSeenByUser(placeId: String, userId: String): Boolean {
        val user = userRepository.findById(userId)

        return if (user.isPresent) {
            user.get().visitedPlaces.contains(placeId)
        } else {
            false
        }
    }

    fun save(user: User): User {
        return userRepository.save(user)
    }
}