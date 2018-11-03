package quantum.complex

import org.junit.Test
import quantum.blocks.Complex
import kotlin.test.assertEquals

class ComplexOperationTest {

    @Test
    fun testDivNumber() {
        val div = Complex(1.0, 2.0) / 2.0
        assertEquals(Complex(0.5, 1.0), div)
    }

    @Test
    fun testPlusComplex() {
        val sum = Complex(1.0, 2.0) + Complex(3.0, 4.0)
        assertEquals(Complex(4.0, 6.0), sum)
    }

    @Test
    fun testMulComplex() {
        assertEquals(Complex(6.0), Complex(2.0) * Complex(3.0))
        assertEquals(Complex(-6.0), Complex(0.0, 2.0) * Complex(0.0, 3.0))
        assertEquals(Complex(-5.0, 10.0), Complex(1.0, 2.0) * Complex(3.0, 4.0))
    }
}