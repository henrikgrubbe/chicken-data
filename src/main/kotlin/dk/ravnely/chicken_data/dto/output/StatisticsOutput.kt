package dk.ravnely.chicken_data.dto.output

import dk.ravnely.chicken_data.entity.Statistics
import org.eclipse.microprofile.openapi.annotations.media.Schema
import java.time.LocalDate

data class StatisticsOutput(
    @field:Schema(required = true)
    val from: LocalDate,

    @field:Schema(required = true)
    val to: LocalDate,

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
    val pricePerEgg: Double,

    @field:Schema(required = true)
    val priceForNextEgg: Double,

    @field:Schema(required = true)
    val daysWithChickens: Long
) {
    companion object {
        fun fromInternal(source: Statistics): StatisticsOutput {
            return StatisticsOutput(
                from = source.from,
                to = source.to,
                expenses = source.expenses,
                income = source.income,
                saved = source.saved,
                balance = source.balance,
                numberOfEggs = source.numberOfEggs,
                pricePerEgg = source.pricePerEgg,
                priceForNextEgg = source.priceForNextEgg,
                daysWithChickens = source.daysWithChickens
            )
        }
    }
}
