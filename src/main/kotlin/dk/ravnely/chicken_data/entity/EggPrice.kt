package dk.ravnely.chicken_data.entity

import java.time.LocalDate

data class EggPrice(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val price: Double
)
