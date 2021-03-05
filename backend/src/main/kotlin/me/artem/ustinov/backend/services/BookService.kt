package me.artem.ustinov.backend.services

import me.artem.ustinov.backend.entities.Book
import me.artem.ustinov.backend.repositories.BookRepository
import org.springframework.stereotype.Service
import javax.security.auth.login.LoginException

@Service
class BookService(private val bookRepository: BookRepository) {

    fun getBookById(id: Long, userId: Long): Book? =
        bookRepository.findByIdAndUserId(id, userId).let {
            if (it.isPresent) {
                it.get()
            } else {
                null
            }
        }

    fun getAllBooks(userId: Long): List<Book> {
        return bookRepository.findAllByUserId(userId)
    }

    fun addBook(book: Book, userId: Long): Book {
        book.userId = userId
        return bookRepository.save(book)
    }

    fun setBookById(id: Long, book: Book, userId: Long): Book {
        book.userId = userId
        book.id = id
        return bookRepository.save(book)
    }

    fun removeBookById(id: Long, userId: Long) {
        bookRepository.deleteByIdAndUserId(id, userId)
    }
}