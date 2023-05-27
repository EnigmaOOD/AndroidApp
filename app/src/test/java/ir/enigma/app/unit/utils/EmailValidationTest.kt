package ir.enigma.app.unit.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.coEvery
import io.mockk.mockkObject
import ir.enigma.app.ui.auth.isValidEmailAddress
import ir.enigma.app.util.MyLog
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


class EmailValidationTest {
    @Test
    fun `test isValidEmail`() {


        mockkObject(MyLog)
        coEvery { MyLog.log(any(), any(), any(), any(), any()) } returns Unit


        //test valid emails
        assert(isValidEmailAddress("ee@frt.com"))       // Act and Assert
        // test invalid emails
        assert(!isValidEmailAddress("ee@frt"))
        assert(!isValidEmailAddress("ee@fr/t.com"))

    }
}