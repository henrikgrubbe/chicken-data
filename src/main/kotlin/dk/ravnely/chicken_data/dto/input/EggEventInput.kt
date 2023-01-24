package dk.ravnely.chicken_data.dto.input

import dk.ravnely.chicken_data.entity.EggEvent
import org.eclipse.microprofile.openapi.annotations.media.Schema
import java.time.Instant
import java.time.LocalDate

data class EggEventInput(
    @field:Schema(required = true)
    val amount: Int,

    @field:Schema(required = true)
    val date: LocalDate,
)

fun EggEventInput.toInternal(): EggEvent {
    return EggEvent().also { eggEvent ->
        eggEvent.amount = amount
        eggEvent.date = date
        eggEvent.createdDateTime = Instant.now()
    }
}
