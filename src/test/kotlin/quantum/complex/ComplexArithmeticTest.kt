package quantum.complex

import org.junit.Test
import quantum.blocks.*
import kotlin.test.assertEquals

class ComplexArithmeticTest {

    val i = ComplexI

    @Test
    fun testSum() {

        assertEquals(Complex(1.0, 1.0), 1.0 + i)
        assertEquals(Complex(1.0, 1.0), i + 1.0 )
    }

    @Test
    fun testMinus() {

        assertEquals(Complex(3.0, -1.0), 3.0 - i)
        assertEquals(Complex(-3.0, 1.0), i - 3.0)
    }

    @Test
    fun testTimes() {

        assertEquals(Complex(0.0, 3.0), 3.0 * i)
        assertEquals(Complex(0.0, 3.0), i * 3.0)
    }

    @Test
    fun testExpression() {

        assertEquals(Complex(6.0, 8.0), 2.0 * (3.0 + 4.0 * i))
        assertEquals(Complex(6.0, 8.0), (3.0 + 4.0 * i) * 2.0)
    }
}