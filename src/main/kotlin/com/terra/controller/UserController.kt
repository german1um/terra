package com.terra.controller

import com.terra.dto.HiddenPlaceDto
import com.terra.dto.OpenPlaceDto
import com.terra.dto.UserDto
import com.terra.model.Token
import com.terra.service.PlaceService
import com.terra.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
        @Autowired val userService: UserService,
        @Autowired val placeService: PlaceService
) {

    @GetMapping("/login")
    fun login(token: String, provider: Int): UserDto {

        return userService.login(Token(token, provider))
    }

    @PostMapping("/users/{userId}/mark-place-seen")
    fun markPlaceSeen(@PathVariable userId: String, placeId: String): OpenPlaceDto {
        userService.markPlaceAsSeen(userId, placeId)

        val place = placeService.getPlaceById(placeId)
        place.timesVisited++
        placeService.save(place)

        return OpenPlaceDto(
                placeService.getPlaceById(placeId)
        )
    }
}