package com.terra.service

import com.terra.dto.PlaceDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserActionService(@Autowired val userService: UserService, @Autowired val placeService: PlaceService) {
    fun getAllPlacesForUser(id: String): List<PlaceDto> {
        val allPlaces = placeService.getAllPlaces()
        val user = userService.getById(id)

        return allPlaces.map { PlaceDto(it, user) }
    }
}