package quantum

import org.junit.Test
import quantum.blocks.Complex
import quantum.blocks.Complex.Companion.One
import quantum.blocks.Complex.Companion.Zero
import quantum.blocks.Qubit
import kotlin.math.sqrt
import kotlin.test.assertEquals

class QubitTest {

    @Test
    fun testFromComplex() {

        val qubit1 = Qubit.from(One, Zero)
        assertEquals(One, qubit1.zero)
        assertEquals(Zero, qubit1.one)

        val qubit2 = Qubit.from(Zero, One)
        assertEquals(Complex(0.0, 0.0), qubit2.zero)
        assertEquals(Complex(1.0, 0.0), qubit2.one)

        val r = 1 / sqrt(2.0)
        val c = Complex(r)

        val qubit3 = Qubit.from(One, One)
        assertEquals(c, qubit3.zero)
        assertEquals(c, qubit3.one)
        assertEquals(c, qubit3.get(0))
        assertEquals(c, qubit3.get(1))
    }
}

