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
) : BaseEntity()