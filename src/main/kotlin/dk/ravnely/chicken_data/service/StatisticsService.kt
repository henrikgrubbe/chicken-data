package dk.ravnely.chicken_data.service

import dk.ravnely.chicken_data.entity.EggEvent
import dk.ravnely.chicken_data.entity.EggPrice
import dk.ravnely.chicken_data.entity.TransactionEvent
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class StatisticsService @Inject constructor(
    val eggEventService: EggEventService, val transactionEventService: TransactionEventService
) {

    private val firstChickenDate = LocalDate.of(2023, 1, 31)
    private val eggPrices = listOf(
        EggPrice(
            startDate = firstChickenDate,
            endDate = LocalDate.of(2024, 1, 1),
            price = 3.0
        )
    )

    fun calculateExpenses(from: LocalDate, to: LocalDate): Double {
        return transactionEventService.getTransactionEvents(from, to)
            .filter { it.amount < 0 }
            .sumOf(TransactionEvent::amount) * -1
    }

    fun calculateIncome(from: LocalDate, to: LocalDate): Double {
        return transactionEventService.getTransactionEvents(from, to)
            .filter { it.amount > 0 }
            .sumOf(TransactionEvent::amount)
    }

    fun calculateNumberOfEggs(from: LocalDate, to: LocalDate): Int {
        return eggEventService.getEggEvents(from, to)
            .sumOf(EggEvent::amount)
    }

    fun calculateSaved(from: LocalDate, to: LocalDate): Double {
        return eggEventService.getEggEvents(from, to)
            .sumOf { getEggPriceByLocalDate(it.date) * it.amount }
    }

    private fun getEggPriceByLocalDate(date: LocalDate): Double {
        return eggPrices.find {
            (date.isAfter(it.startDate) || date.isEqual(it.startDate)) && date.isBefore(it.endDate)
        }?.price ?: 3.0
    }

    fun calculateDaysWithChickens(): Long {
        return ChronoUnit.DAYS.between(firstChickenDate, LocalDate.now())
    }
}
