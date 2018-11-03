package quantum

import quantum.blocks.Complex

import kotlin.test.assertEquals
import org.junit.Test
import quantum.blocks.ComplexOne
import quantum.blocks.ComplexZero
import quantum.blocks.Qubit
import kotlin.math.sqrt

class QubitTest {

    @Test
    fun testFromComplex() {

        val qubit1 = Qubit.from(ComplexOne, ComplexZero)
        assertEquals(ComplexOne, qubit1.zero)
        assertEquals(ComplexZero, qubit1.one)

        val qubit2 = Qubit.from(ComplexZero, ComplexOne)
        assertEquals(Complex(0.0, 0.0), qubit2.zero)
        assertEquals(Complex(1.0, 0.0), qubit2.one)

        val r = 1 / sqrt(2.0)
        val c = Complex(r)

        val qubit3 = Qubit.from(ComplexOne, ComplexOne)
        assertEquals(c, qubit3.zero)
        assertEquals(c, qubit3.one)
        assertEquals(c, qubit3.get(0))
        assertEquals(c, qubit3.get(1))
    }
}

