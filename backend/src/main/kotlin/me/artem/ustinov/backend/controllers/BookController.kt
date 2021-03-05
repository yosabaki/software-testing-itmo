package me.artem.ustinov.backend.controllers

import me.artem.ustinov.backend.entities.Book
import me.artem.ustinov.backend.services.BookService
import me.artem.ustinov.backend.services.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.GET
import org.springframework.web.bind.annotation.RequestMethod.POST
import org.springframework.web.bind.annotation.RequestMethod.PUT
import org.springframework.web.bind.annotation.RestController
import javax.security.auth.login.LoginException

@RestController
@RequestMapping("/books")
class BookController(@Autowired val service: BookService, @Autowired val tokenService: TokenService) {

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: Long, @CookieValue("library-token") token: String): Book? {
        val userId = tokenService.getUserIdByToken(token) ?: throw LoginException()
        return service.getBookById(id, userId)
    }

    @GetMapping
    fun getAllBooks(@CookieValue("library-token") token: String): List<Book> {
        println(token)
        val userId = tokenService.getUserIdByToken(token) ?: throw LoginException()
        println(userId)
        return service.getAllBooks(userId)
    }

    @PostMapping
    fun addBook(@RequestBody book: Book, @CookieValue("library-token") token: String): Book {
        val userId = tokenService.getUserIdByToken(token) ?: throw LoginException()
        return service.addBook(book, userId)
    }

    @PutMapping("/{id}")
    fun updateBookById(@RequestBody book: Book, @PathVariable id: Long, @CookieValue("library-token") token: String): Book {
        val userId = tokenService.getUserIdByToken(token) ?: throw LoginException()
        return service.setBookById(id, book, userId)
    }

    @DeleteMapping("/{id}")
    fun removeBookById(@PathVariable id: Long, @CookieValue("library-token") token: String) {
        val userId = tokenService.getUserIdByToken(token) ?: throw LoginException()
        return service.removeBookById(id, userId)
    }


}