package ir.enigma.app.unit.utils

import ir.enigma.app.util.toPrice
import junit.framework.TestCase
import org.junit.Test

class DoubleToPriceTest {
    @Test
    fun `test zero price`() {
        val price = 0.0
        TestCase.assertEquals("0", price.toPrice())
    }

    @Test
    fun `test positive price without decimal points`() {
        val price = 100.0
        TestCase.assertEquals("100", price.toPrice())
    }

    @Test
    fun `test negative price without decimal points`() {
        val price = -100.0
        TestCase.assertEquals("100- USD", price.toPrice(currency = "USD"))
    }

    @Test
    fun `test negative price with decimal points`() {
        val price = -1234.56
        TestCase.assertEquals("1,234.56- USD", price.toPrice(currency = "USD"))
    }
}