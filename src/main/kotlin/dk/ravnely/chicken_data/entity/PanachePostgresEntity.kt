package dk.ravnely.chicken_data.entity

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class PanachePostgresEntity : PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null
    override fun toString() = "${javaClass.simpleName}<$id>"
}
