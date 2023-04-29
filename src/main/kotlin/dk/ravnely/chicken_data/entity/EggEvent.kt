package dk.ravnely.chicken_data.entity

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.panache.common.Sort
import java.time.Instant
import java.time.LocalDate
import jakarta.persistence.Entity

@Entity(name = "egg_events")
class EggEvent : PanachePostgresEntity() {
    var amount: Int = 0
    lateinit var date: LocalDate
    lateinit var createdDateTime: Instant

    companion object : PanacheCompanion<EggEvent> {
        fun listAllByDate(from: LocalDate, to: LocalDate) = list(
            "date >= ?1 and date < ?2", Sort.by("date"), from, to
        )
    }
}
