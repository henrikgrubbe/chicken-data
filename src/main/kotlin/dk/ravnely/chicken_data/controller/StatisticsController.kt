package dk.ravnely.chicken_data.controller

import dk.ravnely.chicken_data.dto.output.StatisticsOutput
import dk.ravnely.chicken_data.service.StatisticsService
import java.time.LocalDate
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType


@Path("/statistics")
class StatisticsController @Inject constructor(val statisticsService: StatisticsService) {

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    fun getStats(
        @QueryParam("from") from: LocalDate,
        @QueryParam("to") to: LocalDate,
    ): StatisticsOutput {
        val expenses = statisticsService.calculateExpenses(from, to)
        val income = statisticsService.calculateIncome(from, to)
        val saved = statisticsService.calculateSaved(from, to)
        return StatisticsOutput(
            expenses = expenses,
            income = income,
            saved = saved,
            balance = income + saved - expenses,
            numberOfEggs = statisticsService.calculateNumberOfEggs(from, to),
            daysWithChickens = statisticsService.calculateDaysWithChickens()
        )
    }
}
