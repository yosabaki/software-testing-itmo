package me.artem.ustinov.backend

import javax.persistence.Entity


@Entity
class Book(
    var title: String,
    var description: String = "",
    var readPages: Int = 0,
    var totalPages: Int
) : BaseEntity()