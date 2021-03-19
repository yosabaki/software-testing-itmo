package me.artem.ustinov.backend

import me.artem.ustinov.backend.entities.Book
import me.artem.ustinov.backend.repositories.BookRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*

val postgreSQLContainer = PostgreSQLContainer<Nothing>("postgres:13").apply {
    withDatabaseName("integration-tests-db")
    withUsername("artem")
    withPassword("artem")
    start()
}


@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = [BookControllerTest.Initializer::class])
class BookControllerTest {

    @Autowired
    lateinit var bookRepository: BookRepository

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            TestPropertyValues.of(
                "spring.datasource.url=" + postgreSQLContainer.jdbcUrl,
                "spring.datasource.username=" + postgreSQLContainer.username,
                "spring.datasource.password=" + postgreSQLContainer.password
            ).applyTo(configurableApplicationContext.environment)
        }
    }

    @Test
    fun testFindByIdAndUserId() {
        val books = bookRepository.findAllByUserId(1)
        assertThat(bookRepository.findByIdAndUserId(books[0].id!!, 1).get().id)
            .isEqualTo(books[0].id!!)
        val book: Optional<Book> = bookRepository.findByIdAndUserId(books[0].id!!, 2)
        assert(!book.isPresent)
    }

    @Test
    fun testDeleteByIdAndUserId() {
        val books = bookRepository.findAllByUserId(1)
        bookRepository.deleteByIdAndUserId(books[0].id!!, 1)
        assertThat(bookRepository.findAllByUserId(1)).doesNotContain(books[0])

    }

    @BeforeEach
    private fun insertUsers() {
        bookRepository.deleteAll()
        bookRepository.save(Book("title0", "desc0", 0, 0, 1))
        bookRepository.save(Book("title1", "desc1", 0, 0, 2))
        bookRepository.save(Book("title2", "desc2", 0, 0, 2))
        bookRepository.save(Book("title3", "desc3", 0, 0, 1))
        bookRepository.flush()
    }
}
