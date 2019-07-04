package com.terra.controller.admin

import com.terra.model.Place
import com.terra.repository.PlaceRepository
import com.terra.service.PlaceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin")
class PlaceAdminController(@Autowired val placeService: PlaceService, @Autowired val placeRepository: PlaceRepository) {


    @GetMapping("/places")
    fun places(): List<Place> {
        return placeService.getAllPlaces()
    }

    @PostMapping("/places")
    fun save(place: Place) {
        placeService.save(place)
    }

    @PostMapping("/places/{id}/delete")
    fun delete(@PathVariable("id") id: String) {
        placeService.delete(id)
    }
}