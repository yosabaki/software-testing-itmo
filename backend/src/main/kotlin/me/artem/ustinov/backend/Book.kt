package me.artem.ustinov.backend

import javax.persistence.Column
import javax.persistence.Entity


@Entity
class Book(
    var title: String,
    @Column(length= 2000)
    var description: String = "",
    var readPages: Int = 0,
    var totalPages: Int
) : BaseEntity()