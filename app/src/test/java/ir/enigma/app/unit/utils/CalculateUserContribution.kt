package ir.enigma.app.unit.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.coEvery
import io.mockk.mockkObject
import ir.enigma.app.model.Contribution
import ir.enigma.app.model.Purchase
import ir.enigma.app.model.User
import ir.enigma.app.ui.group.util.calculateUserContribution
import ir.enigma.app.util.MyLog
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


class CalculateUserContribution {

    @Before
    fun setUp() {
        mockkObject(MyLog)
        coEvery { MyLog.log(any(), any(), any(), any(), any()) } returns Unit
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

        assertEquals(calculateUserContribution(user1, purchase) , 15000.0)            // Act and Assert

    }




}