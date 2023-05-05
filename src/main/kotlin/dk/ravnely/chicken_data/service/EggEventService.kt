package dk.ravnely.chicken_data.service

import dk.ravnely.chicken_data.entity.EggEvent
import io.quarkus.cache.CacheInvalidateAll
import io.quarkus.cache.CacheResult
import java.time.LocalDate
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class EggEventService {

    @CacheResult(cacheName = "eggEvents")
    fun getEggEvents(from: LocalDate, to: LocalDate): List<EggEvent> {
        return EggEvent.listAllByDate(from, to)
    }

    fun getEggEvent(id: Long): EggEvent? {
        return EggEvent.findById(id)
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "eggEvents")
    @CacheInvalidateAll(cacheName = "statistics")
    fun createEggEvent(eggEvent: EggEvent): EggEvent {
        eggEvent.persist()

        return eggEvent
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "eggEvents")
    @CacheInvalidateAll(cacheName = "statistics")
    fun updateEggEvent(id: Long, eggEvent: EggEvent): EggEvent? {
        val savedEggEvent = EggEvent.findById(id)?.apply {
            amount = eggEvent.amount
            date = eggEvent.date
        }

        return savedEggEvent
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "eggEvents")
    @CacheInvalidateAll(cacheName = "statistics")
    fun deleteEggEvent(id: Long): Boolean {
        return EggEvent.deleteById(id)
    }
}
