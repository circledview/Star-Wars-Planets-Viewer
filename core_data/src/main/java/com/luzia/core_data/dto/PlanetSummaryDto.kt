package com.luzia.core_data.dto

import com.luzia.core_domain.model.PlanetSummary
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PlanetSummaryDto(
    @SerialName("uid")
    val uid: String,
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)

internal fun PlanetSummaryDto.toPlanetSummary() = PlanetSummary(
    uid = uid,
    name = name,
    url = url
)
