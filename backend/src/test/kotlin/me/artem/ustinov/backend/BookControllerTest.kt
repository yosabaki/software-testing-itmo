package me.artem.ustinov.backend

import me.artem.ustinov.backend.entities.Book
import me.artem.ustinov.backend.repositories.BookRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.PostgreSQLContainer
import java.util.*

class MyPostgreSQLContainer(imageName: String) : PostgreSQLContainer<MyPostgreSQLContainer>(imageName)

private val postgreSQLContainer: MyPostgreSQLContainer = MyPostgreSQLContainer("postgres:13")
    .withDatabaseName("integration-tests-db")
    .withUsername("artem")
    .withPassword("artem")
    .withInitScript("init_db.sql").also { it.start() }


@SpringBootTest
@ContextConfiguration(initializers = [BookControllerTest.Initializer::class])
class BookControllerTest {

    @Autowired
    private lateinit var bookRepository: BookRepository

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            TestPropertyValues.of(
                "spring.datasource.url=${postgreSQLContainer.jdbcUrl}",
                "spring.datasource.username=${postgreSQLContainer.username}",
                "spring.datasource.password=${postgreSQLContainer.password}",
                "spring.jpa.hibernate.ddl-auto=none"
            ).applyTo(configurableApplicationContext.environment)
        }
    }

    @Test
    @Transactional
    fun testFindByIdAndUserId() {
        val books = bookRepository.findAllByUserId(1)
        assertThat(bookRepository.findByIdAndUserId(books[0].id!!, 1).get().id)
            .isEqualTo(books[0].id!!)
        val book: Optional<Book> = bookRepository.findByIdAndUserId(books[0].id!!, 2)
        assert(!book.isPresent)
    }

    @Test
    @Transactional
    fun testDeleteByIdAndUserId() {
        val books = bookRepository.findAllByUserId(1)
        print(bookRepository.findAll())
        bookRepository.deleteByIdAndUserId(books[0].id!!, 1)
        assertThat(bookRepository.findAllByUserId(1)).doesNotContain(books[0])
    }
}
