package me.artem.ustinov.backend.repositories

import me.artem.ustinov.backend.entities.UserToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface UserTokenRepository : JpaRepository<UserToken, Long> {
    @Query("select * from user_tokens where token = :token", nativeQuery = true)
    fun findByToken(@Param("token") token: String) : Optional<UserToken>

    @Modifying
    @Transactional
    @Query("delete from user_tokens where token = :token", nativeQuery = true)
    fun deleteByToken(@Param("token") token: String)
}