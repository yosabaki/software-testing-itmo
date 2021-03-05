package me.artem.ustinov.backend.services

import me.artem.ustinov.backend.entities.User
import me.artem.ustinov.backend.entities.UserToken
import me.artem.ustinov.backend.repositories.UserRepository
import me.artem.ustinov.backend.repositories.UserTokenRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.security.auth.login.LoginException

@Service
class RegisterService(private val userRepository: UserRepository, private val tokenRepository: UserTokenRepository) {
    fun register(user: User): Pair<User, UUID> {
        val registeredUser = userRepository.save(user)
        val token = generateToken(registeredUser.id!!)
        return registeredUser to token
    }

    fun login(user: User): Pair<User?, UUID?> {
        val foundUser: Optional<User> = userRepository.findByUsername(user.username)
        return if (foundUser.isPresent) {
            foundUser.get().let {
                if (it.password == user.password) {
                    val token = generateToken(it.id!!)
                    it to token
                } else {
                    throw LoginException()
                }
            }
        } else {
            return null to null
        }
    }

    fun logout(token: String?) = token?.let {
        tokenRepository.deleteByToken(it)
    }

    fun findToken(token: String): User? {
        println(token)
        val userId = tokenRepository.findByToken(token).let {
            if (it.isPresent) {
                println(it.get().userId)
                it.get().userId
            } else {
                null
            }
        }
        return userId?.let {
            userRepository.findById(userId).let {
                if (it.isPresent) {
                    it.get()
                } else {
                    null
                }
            }
        }
    }


    fun generateToken(userId: Long): UUID {
        val token = UUID.randomUUID()
        tokenRepository.save(UserToken(userId, token))
        return token
    }
}