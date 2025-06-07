package com.luzia.core_data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PlanetListResponseDto(
    @SerialName("message")
    val message: String,
    @SerialName("total_records")
    val totalRecords: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("previous")
    val previous: String? = null,
    @SerialName("next")
    val next: String? = null,
    @SerialName("results")
    val results: List<PlanetSummaryDto>
)