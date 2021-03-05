package me.artem.ustinov.backend.repositories

import me.artem.ustinov.backend.entities.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface BookRepository : JpaRepository<Book, Long> {
    @Query("select * from books where id = :id and user_id = :userId", nativeQuery = true)
    fun findByIdAndUserId(@Param("id") id: Long, @Param("userId") userId: Long): Optional<Book>

    @Query("select * from books where user_id = :userId", nativeQuery = true)
    fun findAllByUserId(@Param("userId") userId: Long): List<Book>


    @Modifying
    @Transactional
    @Query("delete from books where user_id = :userId and id = :id", nativeQuery = true)
    fun deleteByIdAndUserId(@Param("id") id: Long, @Param("userId") userId: Long)

}