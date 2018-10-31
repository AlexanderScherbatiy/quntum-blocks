package quantum

import quantum.blocks.Complex

import kotlin.test.assertEquals
import org.junit.Test

class ComplexTest {

    @Test
    fun testComplexDefaultConstructor() {
        val complex = Complex(2.0, 3.0)
        assertEquals(2.0, complex.real)
        assertEquals(3.0, complex.imaginary)
    }


    @Test
    fun testZero() {
        assertEquals(Complex(0.0, 0.0), Complex.ZERO)
    }

    @Test
    fun testPlusComplex() {
        val sum = Complex(1.0, 2.0) + Complex(3.0, 4.0)
        assertEquals(Complex(4.0, 6.0), sum)
    }

}

