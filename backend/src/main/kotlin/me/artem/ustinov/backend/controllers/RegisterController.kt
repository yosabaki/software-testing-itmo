package me.artem.ustinov.backend.controllers

import me.artem.ustinov.backend.entities.User
import me.artem.ustinov.backend.exceptions.LoginException
import me.artem.ustinov.backend.services.RegisterService
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/users", method = [RequestMethod.POST, RequestMethod.GET])
class RegisterController(private val service: RegisterService) {
    @PostMapping("/register")
    fun registerUser(@RequestBody requestUser: User, response: HttpServletResponse) : User {
        val (user, token) = service.register(User(requestUser.username, DigestUtils.sha256Hex(requestUser.password)))
        println(token.toString())
        response.addCookie(Cookie("library-token", token.toString()).apply {
            maxAge = 24 * 60 * 60
            path = "/"
        })
        return user
    }

    @PostMapping("/logout")
    fun logout(@CookieValue(name="library-token", required = false) token: String?, response: HttpServletResponse) {
        response.addCookie(Cookie("library-token", null).apply {
            maxAge = 0
            path="/"
        })
        response.addCookie(Cookie("library-token", null).apply {
            maxAge = 0
            path="/users"
        })
        service.logout(token)
    }

    @PostMapping("/login")
    fun authorize(@RequestBody requestUser: User, response: HttpServletResponse) : ResponseEntity<User> {
        val (user, token) = service.login(User(requestUser.username, DigestUtils.sha256Hex(requestUser.password)))
        if (user != null && token != null) {
            println(token.toString())
            val cookie = Cookie("library-token", token.toString())
            cookie.maxAge = 24 * 60 * 60
            cookie.path = "/"
            response.addCookie(cookie)
            return ResponseEntity<User>(user, HttpStatus.OK)
        } else {
            throw LoginException()
        }
    }

    @GetMapping
    fun checkConnection(@CookieValue(name="library-token", required = false) token: String?, httpServletResponse: HttpServletResponse) : User? {
        return token?.let { service.findToken(token) }
    }
}