package ir.enigma.app.unit

import androidx.test.ext.junit.runners.AndroidJUnit4
import ir.enigma.app.model.Contribution
import ir.enigma.app.model.Purchase
import ir.enigma.app.model.User
import ir.enigma.app.ui.auth.isValidEmailAddress
import ir.enigma.app.ui.group.util.calculateUserContribution
import ir.enigma.app.util.toPrice
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UtilsTest {

    // isValidEmail test
    @Test
    fun `test isValidEmail`() {
        //test valid emails
        assert(isValidEmailAddress("ee@frt.com"))       // Act and Assert
        // test invalid emails
        assert(!isValidEmailAddress("ee@frt"))
        assert(!isValidEmailAddress("ee@fr/t.com"))

    }

    // test calculateUserContribution method
    @Test
    fun `test calculateUserContribution`() {
        val user1 = User(1, "test", "test", 2, "test")      // Arrange
        val user2 = User(2, "test2", "test2", 2, "test2")
        val purchase = Purchase(
            title = "test2",
            "2022-02-02",
            totalPrice = 20000.0,
            sender = User(1, "test", "test", 2, "test"),
            purchaseCategoryIndex = 2,
            buyers = listOf(
                Contribution(
                    user1, 20000.0
                ),
            ),
            consumers = listOf(
                Contribution(
                    user2, 15000.0
                ),
                Contribution(
                    user1, 5000.0
                ),
            )
        )

        assert(calculateUserContribution(user1, purchase) == 5000.0)            // Act and Assert

    }


    @Test
    fun `test zero price`() {
        val price = 0.0
        assertEquals("0", price.toPrice())
    }

    @Test
    fun `test positive price without decimal points`() {
        val price = 100.0
        assertEquals("100", price.toPrice())
    }

    @Test
    fun `test negative price without decimal points`() {
        val price = -100.0
        assertEquals("-100 USD", price.toPrice(currency = "USD"))
    }

    @Test
    fun `test negative price with decimal points`() {
        val price = -1234.56
        assertEquals("-1,234.56 USD", price.toPrice(currency = "USD"))
    }

}