package dk.ravnely.chicken_data.dto.input

import dk.ravnely.chicken_data.entity.TransactionEvent
import org.eclipse.microprofile.openapi.annotations.media.Schema
import java.time.Instant
import java.time.LocalDate

data class TransactionEventInput(
    @field:Schema(required = true)
    val amount: Double,

    @field:Schema(required = true)
    val note: String,

    @field:Schema(required = true)
    val date: LocalDate,
)

fun TransactionEventInput.toInternal(): TransactionEvent {
    return TransactionEvent().also { transactionEvent ->
        transactionEvent.amount = amount
        transactionEvent.note = note
        transactionEvent.date = date
        transactionEvent.createdDateTime = Instant.now()
    }
}
