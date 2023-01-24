package dk.ravnely.chicken_data.dto.output

import dk.ravnely.chicken_data.entity.EggEvent
import org.eclipse.microprofile.openapi.annotations.media.Schema
import java.time.Instant

data class EggEventOutput(
    @field:Schema(required = true)
    val id: Long,

    @field:Schema(required = true)
    val amount: Int,

    @field:Schema(required = true)
    val createdDateTime: Instant,
) {
    companion object {
        fun fromInternal(source: EggEvent): EggEventOutput {
            return EggEventOutput(
                id = source.id ?: -1,
                amount = source.amount,
                createdDateTime = source.createdDateTime,
            )
        }
    }
}
