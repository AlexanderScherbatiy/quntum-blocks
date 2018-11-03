package quantum.complex

import org.junit.Test
import quantum.blocks.Complex
import quantum.blocks.ComplexOne
import quantum.blocks.ComplexZero
import kotlin.test.assertEquals

class ComplexMethodTest {
    @Test
    fun testSqr() {
        assertEquals(0.0, ComplexZero.sqr())
        assertEquals(1.0, ComplexOne.sqr())
        assertEquals(5.0, Complex(1, 2).sqr())
    }

    @Test
    fun testNorm() {
        assertEquals(0.0, ComplexZero.sqr())
        assertEquals(1.0, ComplexOne.sqr())
        assertEquals(5.0, Complex(3, 4).norm())
    }
}