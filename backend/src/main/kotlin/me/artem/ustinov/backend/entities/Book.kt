package me.artem.ustinov.backend.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table


@Entity
@Table(name = "books")
class Book(
    var title: String = "",
    @Column(length = 2000)
    var description: String = "",
    var readPages: Int = 0,
    var totalPages: Int = 0,
    var userId: Long? = null
) : BaseEntity() {
    constructor(
        id: Long,
        title: String,
        description: String,
        readPages: Int,
        totalPages: Int,
        userId: Long
    ) : this(title, description, readPages, totalPages, userId) {
        this.id = id
    }
}