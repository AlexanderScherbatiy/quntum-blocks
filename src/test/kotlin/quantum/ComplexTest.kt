package quantum

import quantum.blocks.Complex

import kotlin.test.assertEquals
import org.junit.Test

class ComplexTest {

    @Test
    fun testComplexDefaultConstructor() {
        val complex = Complex(2.0, 3.0)
        assertEquals(2.0, complex.real)
        assertEquals(3.0, complex.image)
    }


    @Test
    fun testZero() {
        assertEquals(0.0, Complex.zero.real)
        assertEquals(0.0, Complex.zero.image)
    }

}

