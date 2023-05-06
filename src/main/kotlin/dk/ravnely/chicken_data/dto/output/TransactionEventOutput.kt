package dk.ravnely.chicken_data.dto.output

import dk.ravnely.chicken_data.entity.TransactionEvent
import java.time.Instant
import java.time.LocalDate
import org.eclipse.microprofile.openapi.annotations.media.Schema

data class TransactionEventOutput(
    @field:Schema(required = true) val id: Long,
    @field:Schema(required = true) val amount: Double,
    @field:Schema(required = true) val note: String,
    @field:Schema(required = true) val date: LocalDate,
    @field:Schema(required = true) val createdDateTime: Instant,
) {
  companion object {
    fun fromInternal(source: TransactionEvent): TransactionEventOutput {
      return TransactionEventOutput(
          id = source.id ?: -1,
          amount = source.amount,
          note = source.note,
          date = source.date,
          createdDateTime = source.createdDateTime,
      )
    }
  }
}
