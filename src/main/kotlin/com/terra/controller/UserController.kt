package com.terra.controller

import com.terra.dto.OpenPlaceDto
import com.terra.dto.UserDto
import com.terra.model.Token
import com.terra.service.PlaceService
import com.terra.service.UserActionService
import com.terra.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
        @Autowired val userService: UserService,
        @Autowired val placeService: PlaceService,
        @Autowired val userActionService: UserActionService
) {

    @GetMapping("/login")
    fun login(token: String, provider: Int): UserDto {
        return userService.login(Token(token, provider))
    }

    @PostMapping("/users/{userId}/mark-place-seen")
    fun markPlaceSeen(@PathVariable userId: String, placeId: String): OpenPlaceDto {
        userActionService.markPlaceAsSeen(userId, placeId)

        val place = placeService.getById(placeId)
        place.timesVisited++
        placeService.save(place)

        return OpenPlaceDto(
                placeService.getById(placeId)
        )
    }
}