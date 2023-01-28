package dk.ravnely.chicken_data.controller

import dk.ravnely.chicken_data.dto.input.TransactionEventInput
import dk.ravnely.chicken_data.dto.input.toInternal
import dk.ravnely.chicken_data.dto.output.TransactionEventOutput
import dk.ravnely.chicken_data.service.TransactionEventService
import java.time.LocalDate
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Path("/transaction-events")
class TransactionEventController @Inject constructor(val transactionEventService: TransactionEventService) {

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    fun getTransactionEvents(
        @QueryParam("from") from: LocalDate,
        @QueryParam("to") to: LocalDate,
    ): List<TransactionEventOutput> {
        return transactionEventService.getTransactionEvents(from, to)
            .map(TransactionEventOutput::fromInternal)
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getTransactionEventById(
        @PathParam("id") id: Long,
    ): Response {
        return transactionEventService.getTransactionEvent(id)
            ?.let(TransactionEventOutput::fromInternal)
            ?.let { Response.ok(it).build() }
            ?: Response.status(Response.Status.NOT_FOUND).build()
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun createTransactionEvent(transactionEventInput: TransactionEventInput): TransactionEventOutput {
        return transactionEventService.createTransactionEvent(transactionEventInput.toInternal())
            .let(TransactionEventOutput::fromInternal)
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun putTransactionEvent(
        @PathParam("id") id: Long,
        transactionEventInput: TransactionEventInput
    ): Response {
        return transactionEventService.updateTransactionEvent(
            id,
            transactionEventInput.toInternal()
        )
            ?.let(TransactionEventOutput::fromInternal)
            ?.let { Response.ok(it).build() }
            ?: Response.status(Response.Status.NOT_FOUND).build()
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    fun deleteTransactionEvent(
        @PathParam("id") id: Long
    ): Response {
        val success = transactionEventService.deleteTransactionEvent(id)
        if (!success) {
            return Response.status(Response.Status.NOT_FOUND).build()
        }

        return Response.noContent().build()
    }
}
