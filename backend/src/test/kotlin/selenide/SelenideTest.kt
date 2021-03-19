package selenide

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.open
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By

@BeforeAll
fun setUp() {
    Configuration.browser = "firefox"
}

val PASSWORD = "2}wF+w@a2X2`Da%jN/b_{LAWq3nE%s.3"

class SelenideTest {

    @BeforeEach
    fun refresh() {
        Selenide.clearBrowserCookies()
        open("http://localhost:8080/")
    }

    private fun loginInTestUser() {
        `$`(By.id("edit-username")).sendKeys("testUser")
        `$`(By.id("edit-password")).sendKeys(PASSWORD)
        `$`("#loginButton").click()
        clear()
    }

    @Test
    fun testLogin() {
        loginInTestUser()
        `$`(By.id("username")).shouldBe(Condition.visible)
        clear()
    }

    private fun clear() {
        while (`$`(By.className("book")).exists()) {
            `$`(By.className("destroy")).click()
        }
    }

    @Test
    fun testAddAndDeleteBook() {
        loginInTestUser()
        `$`(By.className("new-book")).click()
        `$`(By.id("edit-title")).sendKeys("testTitle")
        `$`(By.id("edit-description")).sendKeys("testDescription")
        `$`(By.id("edit-readPages")).sendKeys("0")
        `$`(By.id("edit-totalPages")).sendKeys("0")
        `$`(By.id("saveEditForm")).click()

        `$`(By.className("book")).shouldHave(text("testTitle"), text("testDescription"))
        `$`(By.className("destroy")).click()
        `$`(By.className("book")).shouldNotBe(Condition.exist)
    }
}