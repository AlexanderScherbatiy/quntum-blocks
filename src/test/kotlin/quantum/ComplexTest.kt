package quantum

import quantum.blocks.Complex

import kotlin.test.assertEquals
import org.junit.Test

class ComplexTest {

    @Test
    fun testComplexConstructor() {

        assertEquals(2.0, Complex(2.0, 3.0).real)
        assertEquals(3.0, Complex(2.0, 3.0).imaginary)


        assertEquals(Complex(4.0, 0.0), Complex(4))
        assertEquals(Complex(4.0, 5.0), Complex(4, 5))

        assertEquals(Complex(2.0, 0.0), Complex(2))
    }


    @Test
    fun testConstZero() {
        assertEquals(Complex(0.0, 0.0), Complex.ZERO)
    }

    @Test
    fun testConstOne() {
        assertEquals(Complex(1.0, 0.0), Complex.ONE)
    }

    @Test
    fun testDivNumber() {
        val div = Complex(1.0, 2.0) / 2
        assertEquals(Complex(0.5, 1.0), div)
    }

    @Test
    fun testPlusComplex() {
        val sum = Complex(1.0, 2.0) + Complex(3.0, 4.0)
        assertEquals(Complex(4.0, 6.0), sum)
    }

    @Test
    fun testMulComplex() {
        assertEquals(Complex(6, 0), Complex(2, 0) * Complex(3, 0))
        assertEquals(Complex(-6, 0), Complex(0, 2) * Complex(0, 3))
        assertEquals(Complex(-5, 10), Complex(1, 2) * Complex(3, 4))
    }

    @Test
    fun testSqr() {
        assertEquals(0.0, Complex.ZERO.sqr())
        assertEquals(1.0, Complex.ONE.sqr())
        assertEquals(5.0, Complex(1, 2).sqr())
    }

    @Test
    fun testNorm() {
        assertEquals(0.0, Complex.ZERO.sqr())
        assertEquals(1.0, Complex.ONE.sqr())
        assertEquals(5.0, Complex(3, 4).norm())
    }
}

