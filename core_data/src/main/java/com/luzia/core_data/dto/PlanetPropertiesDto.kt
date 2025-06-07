package com.luzia.core_data.dto

import com.luzia.core_domain.model.PlanetProperties
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PlanetPropertiesDto(
    @SerialName("name")
    val name: String,
    @SerialName("climate")
    val climate: String,
    @SerialName("population")
    val population: String,
    @SerialName("diameter")
    val diameter: String,
    @SerialName("gravity")
    val gravity: String,
    @SerialName("terrain")
    val terrain: String,
    @SerialName("url")
    val url: String,
    @SerialName("surface_water")
    val surfaceWater: String,
    @SerialName("rotation_period")
    val rotationPeriod: String,
    @SerialName("orbital_period")
    val orbitalPeriod: String,
    @SerialName("created")
    val created: String,
    @SerialName("edited")
    val edited: String
)

internal fun PlanetPropertiesDto.toPlanetProperties() = PlanetProperties(
    name = name,
    climate = climate,
    population = population,
    diameter = diameter,
    gravity = gravity,
    terrain = terrain,
    url = url,
    surfaceWater = surfaceWater,
    rotationPeriod = rotationPeriod,
    orbitalPeriod = orbitalPeriod,
    created = created,
    edited = edited
)