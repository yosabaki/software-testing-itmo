package me.artem.ustinov.backend.entities

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user_tokens")
class UserToken() {
    @Id
    var userId: Long = 0
    var token: String? = null
    constructor(
        userId: Long,
        token: UUID
    ) : this() {
        this.userId = userId
        this.token = token.toString()
    }
}