package me.artem.ustinov.backend.services

import me.artem.ustinov.backend.repositories.UserTokenRepository
import org.springframework.stereotype.Service

@Service
class TokenService(private val userTokenRepository: UserTokenRepository) {
    fun getUserIdByToken(token: String): Long? {
        return userTokenRepository.findByToken(token).let {
            println(token)
            if (it.isPresent) {
                it.get().userId
            } else {
                null
            }
        }
    }
}