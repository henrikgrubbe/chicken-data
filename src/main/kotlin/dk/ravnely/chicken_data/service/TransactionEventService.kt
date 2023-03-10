package dk.ravnely.chicken_data.service

import dk.ravnely.chicken_data.entity.TransactionEvent
import io.quarkus.cache.CacheInvalidateAll
import io.quarkus.cache.CacheResult
import java.time.LocalDate
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional

@ApplicationScoped
class TransactionEventService {

    @CacheResult(cacheName = "transactionEvents")
    fun getTransactionEvents(from: LocalDate, to: LocalDate): List<TransactionEvent> {
        return TransactionEvent.listAllByDate(from, to)
    }

    fun getTransactionEvent(id: Long): TransactionEvent? {
        return TransactionEvent.findById(id)
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "transactionEvents")
    fun createTransactionEvent(transactionEvent: TransactionEvent): TransactionEvent {
        transactionEvent.persist()

        return transactionEvent
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "transactionEvents")
    fun updateTransactionEvent(id: Long, transactionEvent: TransactionEvent): TransactionEvent? {
        val savedTransactionEvent = TransactionEvent.findById(id)?.apply {
            amount = transactionEvent.amount
            note = transactionEvent.note
            date = transactionEvent.date
        }

        return savedTransactionEvent
    }

    @Transactional
    @CacheInvalidateAll(cacheName = "transactionEvents")
    fun deleteTransactionEvent(id: Long): Boolean {
        return TransactionEvent.deleteById(id)
    }
}
