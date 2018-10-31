package quantum

import quantum.blocks.Complex

import kotlin.test.assertEquals
import org.junit.Test
import quantum.blocks.Qubit
import kotlin.math.sqrt

class QubitTest {

    @Test
    fun testFromComplex() {

        val qubit1 = Qubit.fromComplex(Complex.ONE, Complex.ZERO)
        assertEquals(Complex(1.0, 0.0), qubit1.zero)
        assertEquals(Complex(0.0, 0.0), qubit1.one)

        val qubit2 = Qubit.fromComplex(Complex.ZERO, Complex.ONE)
        assertEquals(Complex(0.0, 0.0), qubit2.zero)
        assertEquals(Complex(1.0, 0.0), qubit2.one)

        val r = 1 / sqrt(2.0)
        val c = Complex(r)

        val qubit3 = Qubit.fromComplex(Complex.ONE, Complex.ONE)
        assertEquals(c, qubit3.zero)
        assertEquals(c, qubit3.one)
    }
}

