package ir.enigma.app.unit.utils

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import io.mockk.coEvery
import io.mockk.mockkObject
import ir.enigma.app.component.MemberContribution
import ir.enigma.app.model.Contribution
import ir.enigma.app.model.User
import ir.enigma.app.ui.group.checkSumError
import ir.enigma.app.ui.group.getContributionList
import ir.enigma.app.util.MyLog
import ir.enigma.app.util.zeroIfEmpty
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class ZeroIfEmptyTest {

    @Before
    fun setUp() {
        mockkObject(MyLog)
        coEvery { MyLog.log(any(), any(), any(), any(), any()) } returns Unit
    }

    @Test
    fun `returns 0 if string is empty`() {
        // Arrange
        val input = ""

        // Act
        val output = input.zeroIfEmpty()

        // Assert
        assertEquals("0", output)
    }

    @Test
    fun `returns original string if not empty`() {
        // Arrange
        val input = "123"

        // Act
        val output = input.zeroIfEmpty()

        // Assert
        assertEquals(input, output)
    }
}
