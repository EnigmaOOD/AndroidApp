package ir.enigma.app.unit.utils

import io.mockk.coEvery
import io.mockk.mockkObject
import ir.enigma.app.data.ApiResult
import ir.enigma.app.ui.MessageType
import ir.enigma.app.ui.getMessageByResult
import ir.enigma.app.util.MyLog
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class GetMessageByResultTest {

    @Before
    fun setUp() {
        mockkObject(MyLog)
        coEvery { MyLog.log(any(), any(), any(), any(), any()) } returns Unit
    }

    @Test
    fun `returns null if result is null`() {
        // Arrange
        val result: ApiResult<*>? = null

        // Act
        val message = getMessageByResult(result)

        // Assert
        assertNull(message)
    }

    @Test
    fun `returns a success message with type SUCCESS if result is of type ApiResult Success and message is not null or empty`() {
        // Arrange
        val data = "test"
        val messageText = "Success message"
        val result = ApiResult.Success(data, messageText)

        // Act
        val message = getMessageByResult(result)

        // Assert
        assertNotNull(message)
        assertEquals(MessageType.SUCCESS, message?.type)
        assertEquals(messageText, message?.text)
    }

    @Test
    fun `returns null if result is of type ApiResult Success and message is null or empty`() {
        // Arrange
        val data = "test"
        val result = ApiResult.Success(data)

        // Act
        val message = getMessageByResult(result)

        // Assert
        assertNull(message)
    }

    @Test
    fun `returns an error message with type ERROR if result is of type ApiResult Error and message is not null or empty`() {
        // Arrange
        val messageText = "Error message"
        val result = ApiResult.Error(messageText)

        // Act
        val message = getMessageByResult(result)

        // Assert
        assertNotNull(message)
        assertEquals(MessageType.ERROR, message?.type)
        assertEquals(messageText, message?.text)
    }

    @Test
    fun `returns null if result is of type ApiResult Loading`() {
        // Arrange
        val result = ApiResult.Loading()

        // Act
        val message = getMessageByResult(result)

        // Assert
        assertNull(message)
    }

    @Test
    fun `returns null if result is of type ApiResult Empty`() {
        // Arrange
        val result = ApiResult.Empty()

        // Act
        val message = getMessageByResult(result)

        // Assert
        assertNull(message)
    }
}