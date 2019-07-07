package com.terra.controller.domain

import com.terra.dto.HiddenPlaceDto
import com.terra.dto.OpenPlaceDto

data class PlaceResponse(
        val hiddenLists: List<HiddenPlaceDto>,
        val openLists: List<OpenPlaceDto>
)