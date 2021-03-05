package me.artem.ustinov.backend.repositories

import me.artem.ustinov.backend.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    @Query("select * from users where username = :username", nativeQuery = true)
    fun findByUsername(@Param("username") username: String) : Optional<User>
}