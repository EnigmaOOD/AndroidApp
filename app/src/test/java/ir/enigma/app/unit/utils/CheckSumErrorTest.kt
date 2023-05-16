package ir.enigma.app.unit.utils

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import ir.enigma.app.component.MemberContribution
import ir.enigma.app.model.Contribution
import ir.enigma.app.model.User
import ir.enigma.app.ui.group.checkSumError
import ir.enigma.app.ui.group.getContributionList
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CheckSumErrorTest {

    @Test
    fun `returns null if relatedConsumer is true and at least one member has a weight greater than zero`() {
        // Arrange
        val list = SnapshotStateMap<Int, MemberContribution>().apply {
            put(1, MemberContribution(User(1, "test1@test.com", "Test 1", 1), mutableStateOf("0"), mutableStateOf("100")))
            put(2, MemberContribution(User(2, "test2@test.com", "Test 2", 2), mutableStateOf("1"), mutableStateOf("0")))
            put(3, MemberContribution(User(3, "test3@test.com", "Test 3", 3), mutableStateOf("0"), mutableStateOf("0")))
        }

        // Act
        val result = checkSumError(null, list, true)

        // Assert
        assertNull(result)
    }

    @Test
    fun `returns an error message if relatedConsumer is true and no members have a weight greater than zero`() {
        // Arrange
        val list = SnapshotStateMap<Int, MemberContribution>().apply {
            put(1, MemberContribution(User(1, "test1@test.com", "Test 1", 1), mutableStateOf("0"), mutableStateOf("100")))
            put(2, MemberContribution(User(2, "test2@test.com", "Test 2", 2), mutableStateOf("0"), mutableStateOf("0")))
            put(3, MemberContribution(User(3, "test3@test.com", "Test 3", 3), mutableStateOf("0"), mutableStateOf("0")))
        }

        // Act
        val result = checkSumError(null, list, true)

        // Assert
        assertEquals("حداقل وزن یک نفر باید بیشتر از صفر باشد", result)
    }

    @Test
    fun `returns null if priceDouble is equal to the sum of all member contributions and relatedConsumer is false`() {
        // Arrange
        val list = SnapshotStateMap<Int, MemberContribution>().apply {
            put(1, MemberContribution(User(1, "test1@test.com", "Test 1", 1), mutableStateOf("0"), mutableStateOf("100")))
            put(2, MemberContribution(User(2, "test2@test.com", "Test 2", 2), mutableStateOf("1"), mutableStateOf("50")))
            put(3, MemberContribution(User(3, "test3@test.com", "Test 3", 3), mutableStateOf("0"), mutableStateOf("75")))
        }

        // Act
        val result = checkSumError(225.0, list, false)

        // Assert
        assertNull(result)
    }

    @Test
    fun `returns an error message if priceDouble is not equal to the sum of all member contributions and relatedConsumer is false`() {
        // Arrange
        val list = SnapshotStateMap<Int, MemberContribution>().apply {
            put(1, MemberContribution(User(1, "test1@test.com", "Test 1", 1), mutableStateOf("0"), mutableStateOf("100")))
            put(2, MemberContribution(User(2, "test2@test.com", "Test 2", 2), mutableStateOf("1"), mutableStateOf("50")))
            put(3, MemberContribution(User(3, "test3@test.com", "Test 3", 3), mutableStateOf("0"), mutableStateOf("75")))
        }

        // Act
        val result = checkSumError(250.0, list, false)

        // Assert
        assertEquals("مجموع مبلغ ها با قیمت کل خرید برابر نیست", result)
    }

}