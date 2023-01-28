package dk.ravnely.chicken_data.entity

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.panache.common.Sort
import java.time.Instant
import java.time.LocalDate
import javax.persistence.Entity

@Entity(name = "transaction_events")
class TransactionEvent : PanachePostgresEntity() {
    var amount: Double = 0.0
    lateinit var note: String
    lateinit var date: LocalDate
    lateinit var createdDateTime: Instant

    companion object : PanacheCompanion<TransactionEvent> {
        fun listAllByDate(from: LocalDate, to: LocalDate) = list(
            "date >= ?1 and date < ?2", Sort.by("date"), from, to
        )
    }
}
