package dk.ravnely.chicken_data.entity

import java.time.LocalDate

data class Statistics(
    val from: LocalDate,
    val to: LocalDate,
    val expenses: Double,
    val income: Double,
    val saved: Double,
    val balance: Double,
    val numberOfEggs: Int,
    val pricePerEgg: Double,
    val priceForNextEgg: Double,
    val daysWithChickens: Long
)

