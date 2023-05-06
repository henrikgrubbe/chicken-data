package dk.ravnely.chicken_data.service

import dk.ravnely.chicken_data.entity.EggEvent
import dk.ravnely.chicken_data.entity.EggPrice
import dk.ravnely.chicken_data.entity.GroupByUnit
import dk.ravnely.chicken_data.entity.Statistics
import dk.ravnely.chicken_data.entity.TransactionEvent
import io.quarkus.cache.CacheResult
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters

@ApplicationScoped
class StatisticsService
@Inject
constructor(
    val eggEventService: EggEventService,
    val transactionEventService: TransactionEventService
) {
  fun getStatistics(from: LocalDate, to: LocalDate, groupByUnit: GroupByUnit?): List<Statistics> {
    if (groupByUnit == null) {
      return listOf(getStatistics(from, to))
    }

    return from
        .datesUntil(to)
        .toList()
        .groupBy { truncateLocalDate(it, groupByUnit) }
        .map { (_, dates) -> getStatistics(dates.first(), dates.last().plusDays(1)) }
  }

  @CacheResult(cacheName = "statistics")
  fun getStatistics(from: LocalDate, to: LocalDate): Statistics {
    val transactionEvents = transactionEventService.getTransactionEvents(from, to)
    val eggEvents = eggEventService.getEggEvents(from, to)

    return statisticsFromEvents(from, to, transactionEvents, eggEvents)
  }

  companion object {
    private fun statisticsFromEvents(
        from: LocalDate,
        to: LocalDate,
        transactionEvents: List<TransactionEvent>,
        eggEvents: List<EggEvent>
    ): Statistics {
      val expenses = transactionEvents.filter { it.amount < 0 }.sumOf(TransactionEvent::amount) * -1
      val income = transactionEvents.filter { it.amount > 0 }.sumOf(TransactionEvent::amount)
      val saved = eggEvents.sumOf { getEggPriceByLocalDate(it.date) * it.amount }
      val balance = income + saved - expenses
      val numberOfEggs = eggEvents.sumOf(EggEvent::amount)

      return Statistics(
          from = from,
          to = to,
          expenses = expenses,
          income = income,
          saved = saved,
          balance = balance,
          numberOfEggs = numberOfEggs,
          pricePerEgg = if (numberOfEggs == 0) 0.0 else expenses / numberOfEggs,
          priceForNextEgg = expenses / (numberOfEggs + 1),
          daysWithChickens = ChronoUnit.DAYS.between(firstChickenDate, LocalDate.now()))
    }

    private fun getEggPriceByLocalDate(date: LocalDate): Double {
      return eggPrices
          .find {
            (date.isAfter(it.startDate) || date.isEqual(it.startDate)) && date.isBefore(it.endDate)
          }
          ?.price
          ?: 3.0
    }

    private val firstChickenDate = LocalDate.of(2023, 2, 1)
    private val eggPrices =
        listOf(
            EggPrice(
                startDate = LocalDate.of(2023, 1, 1),
                endDate = LocalDate.of(2024, 1, 1),
                price = 3.6))

    private fun truncateLocalDate(localDate: LocalDate, groupByUnit: GroupByUnit): LocalDate {
      return when (groupByUnit) {
        GroupByUnit.DAILY -> localDate
        GroupByUnit.MONTHLY -> localDate.with(TemporalAdjusters.firstDayOfMonth())
        GroupByUnit.YEARLY -> localDate.with(TemporalAdjusters.firstDayOfYear())
      }
    }
  }
}
