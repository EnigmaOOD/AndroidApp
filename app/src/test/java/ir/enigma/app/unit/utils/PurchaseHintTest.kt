package ir.enigma.app.unit.utils

import ir.enigma.app.model.Contribution
import ir.enigma.app.model.Purchase
import ir.enigma.app.model.User
import ir.enigma.app.ui.auth.AuthViewModel

import ir.enigma.app.ui.group.component.getPurchaseHint
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

// Test cases for getPurchaseHint function without consumers
class GetPurchaseHintTest {

    val me = User(0, "test", "me", 0)
    val alice = User(1, "test", "Alice", 0)
    val bob = User(2, "test", "Bob", 0)
    val charlie = User(3, "test", "Charlie", 0)
    val dave = User(4, "test", "Dave", 0)
    val emily = User(5, "test", "Emily", 0)

    @Before
    fun setMe() {
        AuthViewModel.me = me
    }

    // Test case where there are no buyers yet
    @Test
    fun `test getPurchaseHint with no buyers`() {
        val purchase = Purchase(
            "Test purchase 1",
            "2023-05-16",
            20.0,
            User(0, "test", "Joun", 0),
            0,
            emptyList(),
            emptyList()
        )
        val hint = getPurchaseHint(purchase, "USD")
        assertEquals("هیچ کس پرداخت نکرد", hint)
    }

    // Test case where there is only one buyer
    @Test
    fun `test getPurchaseHint with one buyer`() {

        val buyer1 = Contribution(alice, 10.0)
        val purchase =
            Purchase(
                "Test purchase 2",
                "2023-05-15",
                30.0,
                me,
                1,
                listOf(buyer1),
                emptyList()
            )
        val hint = getPurchaseHint(purchase, "یورو")
        assertEquals("Alice 30 یورو پرداخت کرد", hint)
    }

    // Test case where there are two buyers (user is one of them)
    @Test
    fun `test getPurchaseHint with two buyers including user`() {
        val buyer1 = Contribution(alice, 10.0)
        val purchase = Purchase(
            "Test purchase 3",
            "2023-05-14",
            25.0,
            me,
            2,
            listOf(buyer1, Contribution(me, 15.0)),
            emptyList()
        )
        val hint = getPurchaseHint(purchase, "تومان")
        assertEquals("شما و Alice 25 تومان پرداخت کردید", hint)
    }

    // Test case where there are two buyers (user is not one of them)
    @Test
    fun `test getPurchaseHint with two buyers not including user`() {
        val buyer1 = Contribution(me , 10.0)
        val buyer2 = Contribution(bob, 5.0)
        val purchase = Purchase(
            "Test purchase 4",
            "2023-05-13",
            40.0,
            me,
            3,
            listOf(buyer1, buyer2),
            emptyList()
        )
        val hint = getPurchaseHint(purchase, "دلار")
        assertEquals("شما و Bob 40 دلار پرداخت کردید", hint)
    }

    // Test case where there are more than two buyers (user is one of them)
    @Test
    fun `test getPurchaseHint with multiple buyers including user`() {
        val buyer1 = Contribution(alice, 10.0)
        val buyer2 = Contribution(bob, 5.0)
        val buyer3 = Contribution(charlie, 8.0)
        val buyer4 = Contribution(dave, 7.0)
        val purchase = Purchase(
            "Test purchase 5",
            "2023-05-12",
            50.0,
            me,
            4,
            listOf(buyer1, buyer2, Contribution(me, 20.0), buyer3, buyer4),
            emptyList()
        )
        val hint = getPurchaseHint(purchase, "یورو")
        assertEquals("شما و 4 نفر دیگر 50 یورو پرداخت کردید", hint)
    }

    // Test case where there are more than two buyers (user is not one of them)
    @Test
    fun `test getPurchaseHint with multiple buyers not including user`() {
        val buyer1 = Contribution(alice, 10.0)
        val buyer2 = Contribution(bob, 5.0)
        val buyer3 = Contribution(charlie, 8.0)
        val buyer4 = Contribution(dave, 7.0)
        val buyer5 = Contribution(emily, 4.0)
        val purchase = Purchase(
            "Test purchase 6",
            "2023-05-11",
            60.0,
            me,
            5,
            listOf(buyer1, buyer2, buyer3, buyer4, buyer5),
            emptyList()
        )
        val hint = getPurchaseHint(purchase, "تومان")
        assertEquals("Alice و 5 نفر دیگر 60 تومان پرداخت کردند", hint)
    }

}