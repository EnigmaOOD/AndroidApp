package ir.enigma.app.unit.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import ir.enigma.app.ui.auth.isValidEmailAddress
import org.junit.Test
import org.junit.runner.RunWith


class EmailValidationTest {
    @Test
    fun `test isValidEmail`() {
        //test valid emails
        assert(isValidEmailAddress("ee@frt.com"))       // Act and Assert
        // test invalid emails
        assert(!isValidEmailAddress("ee@frt"))
        assert(!isValidEmailAddress("ee@fr/t.com"))

    }
}