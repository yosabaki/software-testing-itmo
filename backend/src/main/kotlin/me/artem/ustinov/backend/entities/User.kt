package me.artem.ustinov.backend.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "users")
class User(
    @Column(unique = true)
    var username: String,
    var password: String,
) : BaseEntity()