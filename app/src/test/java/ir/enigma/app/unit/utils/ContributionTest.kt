package ir.enigma.app.unit.utils

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import ir.enigma.app.component.MemberContribution
import ir.enigma.app.model.Contribution
import ir.enigma.app.model.User
import ir.enigma.app.ui.group.getContributionList
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ContributionTest {

    private lateinit var selectedMembers: SnapshotStateMap<Int, MemberContribution>
    private var totalPrice: Double = 0.0
    private var isRelated: Boolean = false

    val me = User(0, "test", "me", 0)
    val john = User(1, "test", "John", 0)
    val bob = User(2, "test", "Bob", 0)
    val charlie = User(3, "test", "Charlie", 0)
    val dave = User(4, "test", "Dave", 0)
    val emily = User(5, "test", "Emily", 0)

    @Before
    fun setup() {
        selectedMembers = SnapshotStateMap<Int, MemberContribution>()
        selectedMembers[1] = MemberContribution(john, mutableStateOf("1"), mutableStateOf("0"))
        selectedMembers[2] = MemberContribution(emily, mutableStateOf("2"), mutableStateOf("0"))
        selectedMembers[3] = MemberContribution(bob, mutableStateOf("1"), mutableStateOf("0"))
        totalPrice = 100.0
        isRelated = true
    }

    @Test
    fun `test getContributionList with related members`() {
        val expectedContributions = listOf(
            Contribution(john, 25.0),
            Contribution(emily, 50.0),
            Contribution(bob, 25.0)
        )

        val actualContributions = getContributionList(selectedMembers, totalPrice, isRelated)

        assertEquals(expectedContributions.size, actualContributions.size)
        assertTrue(expectedContributions.containsAll(actualContributions))
    }

    @Test
    fun `test getContributionList with exact contributions`() {
        isRelated = false
        selectedMembers[1]!!.exact.value = "30"
        selectedMembers[2]!!.exact.value = "40"
        selectedMembers[3]!!.exact.value = "30"

        val expectedContributions = listOf(
            Contribution(john, 30.0),
            Contribution(emily, 40.0),
            Contribution(bob, 30.0)
        )

        val actualContributions = getContributionList(selectedMembers, totalPrice, isRelated)

        assertEquals(expectedContributions.size, actualContributions.size)
        assertTrue(expectedContributions.containsAll(actualContributions))
    }
}
