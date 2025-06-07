package com.luzia.core_data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PlanetDetailResultDto(
    @SerialName("uid")
    val uid: String,
    @SerialName("description")
    val description: String,
    @SerialName("properties")
    val properties: PlanetPropertiesDto
)
