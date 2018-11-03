package quantum.complex

import org.junit.Test
import quantum.blocks.ComplexI
import quantum.blocks.ComplexOne
import quantum.blocks.ComplexZero
import kotlin.test.assertEquals

class ComplexConstantTest {


    @Test
    fun testConstZero() {
        assertEquals(0.0, ComplexZero.real)
        assertEquals(0.0, ComplexZero.imaginary)
    }

    @Test
    fun testConstOne() {
        assertEquals(1.0, ComplexOne.real)
        assertEquals(0.0, ComplexOne.imaginary)
    }

    @Test
    fun testConstI() {
        assertEquals(0.0, ComplexI.real)
        assertEquals(1.0, ComplexI.imaginary)
    }
}