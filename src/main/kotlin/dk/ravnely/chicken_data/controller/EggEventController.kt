package dk.ravnely.chicken_data.controller

import dk.ravnely.chicken_data.dto.input.EggEventInput
import dk.ravnely.chicken_data.dto.input.toInternal
import dk.ravnely.chicken_data.dto.output.EggEventOutput
import dk.ravnely.chicken_data.service.EggEventService
import java.time.LocalDate
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Path("/egg-events")
class EggEventController @Inject constructor(val eggEventService: EggEventService) {

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    fun getEggEvents(
        @QueryParam("from") from: LocalDate,
        @QueryParam("to") to: LocalDate,
    ): List<EggEventOutput> {
        return eggEventService.getEggEvents(from, to)
            .map(EggEventOutput::fromInternal)
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getEggEventById(
        @PathParam("id") id: Long,
    ): Response {
        return eggEventService.getEggEvent(id)
            ?.let(EggEventOutput::fromInternal)
            ?.let { Response.ok(it).build() }
            ?: Response.status(Response.Status.NOT_FOUND).build()
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun createEggEvent(
        eggEventInput: EggEventInput
    ): EggEventOutput {
        return eggEventService.createEggEvent(eggEventInput.toInternal())
            .let(EggEventOutput::fromInternal)
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun putEggEvent(
        @PathParam("id") id: Long,
        eggEventInput: EggEventInput
    ): Response {
        return eggEventService.updateEggEvent(id, eggEventInput.toInternal())
            ?.let(EggEventOutput::fromInternal)
            ?.let { Response.ok(it).build() }
            ?: Response.status(Response.Status.NOT_FOUND).build()
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    fun deleteEggEvent(
        @PathParam("id") id: Long
    ): Response {
        val success = eggEventService.deleteEggEvent(id)
        if (!success) {
            return Response.status(Response.Status.NOT_FOUND).build()
        }

        return Response.noContent().build()
    }
}
