package me.artem.ustinov.backend

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.client.RestTemplateBuilder

@SpringBootTest
class BackendApplicationTests(@Autowired val restTemplateBuilder: RestTemplateBuilder) {

    @Test
    fun contextLoads() {
//        val restTemplate = restTemplateBuilder.build()
//        val response = restTemplate.getForEntity("/")
    }

}
