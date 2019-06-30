package com.terra.controller

import com.terra.model.Token
import com.terra.model.User
import com.terra.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(@Autowired val userService: UserService) {

    @GetMapping("/login")
    fun login(token: String, provider: Int): User {

        return userService.getOrSave(Token(token, provider))
    }

}