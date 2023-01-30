package dk.ravnely.chicken_data.dto.output

import org.eclipse.microprofile.openapi.annotations.media.Schema

data class StatisticsOutput(
    @field:Schema(required = true)
    val expenses: Double,

    @field:Schema(required = true)
    val income: Double,

    @field:Schema(required = true)
    val saved: Double,

    @field:Schema(required = true)
    val balance: Double,

    @field:Schema(required = true)
    val numberOfEggs: Int,

    @field:Schema(required = true)
    val daysWithChickens: Long
)
