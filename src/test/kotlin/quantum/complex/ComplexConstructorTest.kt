package quantum.complex

import org.junit.Test
import quantum.blocks.Complex
import kotlin.test.assertEquals

class ComplexConstructorTest {

    @Test
    fun testComplexConstructor() {

        assertEquals(2.0, Complex(2.0, 3.0).real)
        assertEquals(3.0, Complex(2.0, 3.0).imaginary)

        assertEquals(Complex(4.0, 0.0), Complex(4.0))
        assertEquals(Complex(4.0, 5.0), Complex(4.0, 5.0))
        assertEquals(Complex(2.0, 0.0), Complex(2.0))
    }
}

