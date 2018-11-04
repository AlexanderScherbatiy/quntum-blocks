package quantum.complex

import org.junit.Test
import quantum.blocks.Complex
import quantum.blocks.ComplexOne
import quantum.blocks.ComplexZero
import kotlin.test.assertEquals

class ComplexMethodTest {

    @Test
    fun testEquals() {
        assertEquals(Complex(0.0, 0.0), Complex(0.0, -0.0))
        assertEquals(Complex(0.0, 0.0), Complex(-0.0, 0.0))
        assertEquals(Complex(0.0, 0.0), Complex(-0.0, -0.0))
    }

    @Test
    fun testHashcode() {

        assertEquals(Complex(0.0, 0.0).hashCode(), Complex(0.0, -0.0).hashCode())
        assertEquals(Complex(0.0, 0.0).hashCode(), Complex(-0.0, 0.0).hashCode())
        assertEquals(Complex(0.0, 0.0).hashCode(), Complex(-0.0, -0.0).hashCode())
    }

    @Test
    fun testSqr() {
        assertEquals(0.0, ComplexZero.sqr())
        assertEquals(1.0, ComplexOne.sqr())
        assertEquals(5.0, Complex(1.0, 2.0).sqr())
    }

    @Test
    fun testNorm() {
        assertEquals(0.0, ComplexZero.sqr())
        assertEquals(1.0, ComplexOne.sqr())
        assertEquals(5.0, Complex(3.0, 4.0).norm())
    }
}