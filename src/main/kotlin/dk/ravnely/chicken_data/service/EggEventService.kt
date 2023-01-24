package dk.ravnely.chicken_data.service

import dk.ravnely.chicken_data.entity.EggEvent
import io.quarkus.cache.CacheInvalidateAll
import io.quarkus.cache.CacheResult
import java.time.LocalDate
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional

@ApplicationScoped
class EggEventService {

    @CacheResult(cacheName = "eggEvents")
    fun getEggEvents(from: LocalDate, to: LocalDate): List<EggEvent> {
        return EggEvent.listAllByDate(from, to)
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "eggEvents")
    fun createEggEvent(eggEvent: EggEvent): EggEvent {
        eggEvent.persist()

        return eggEvent
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "eggEvents")
    fun createOrUpdateEggEvent(id: Long, eggEvent: EggEvent): EggEvent {
        val savedEggEvent = EggEvent.findById(id)?.also {
            it.amount = eggEvent.amount
            it.date = eggEvent.date
        } ?: eggEvent

        savedEggEvent.persist()

        return savedEggEvent
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "eggEvents")
    fun deleteEggEvent(id: Long): Boolean {
        return EggEvent.deleteById(id)
    }
}
