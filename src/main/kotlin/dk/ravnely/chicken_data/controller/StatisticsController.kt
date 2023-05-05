package dk.ravnely.chicken_data.controller

import dk.ravnely.chicken_data.dto.input.GroupByUnitInput
import dk.ravnely.chicken_data.dto.input.toInternal
import dk.ravnely.chicken_data.dto.output.StatisticsOutput
import dk.ravnely.chicken_data.service.StatisticsService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import java.time.LocalDate


@Path("/statistics")
class StatisticsController @Inject constructor(val statisticsService: StatisticsService) {

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    fun getStats(
        @QueryParam("from") from: LocalDate,
        @QueryParam("to") to: LocalDate,
        @QueryParam("unit") groupByUnitInput: GroupByUnitInput?
    ): List<StatisticsOutput> {
        return statisticsService
            .getStatistics(from, to, groupByUnitInput?.toInternal())
            .map(StatisticsOutput::fromInternal)
    }
}
