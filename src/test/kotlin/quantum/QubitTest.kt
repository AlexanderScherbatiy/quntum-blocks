package quantum

import org.junit.Test
import quantum.blocks.Complex.Companion.One
import quantum.blocks.Complex.Companion.Zero
import quantum.blocks.Complex.Companion.complex
import quantum.blocks.Qubit
import kotlin.math.sqrt
import kotlin.test.assertEquals

class QubitTest {

    @Test
    fun testFromcomplex() {

        val qubit1 = Qubit.from(One, Zero)
        assertEquals(One, qubit1.zero)
        assertEquals(Zero, qubit1.one)

        val qubit2 = Qubit.from(Zero, One)
        assertEquals(complex(0.0, 0.0), qubit2.zero)
        assertEquals(complex(1.0, 0.0), qubit2.one)

        val r = 1 / sqrt(2.0)
        val c = complex(r)

        val qubit3 = Qubit.from(One, One)
        assertEquals(c, qubit3.zero)
        assertEquals(c, qubit3.one)
        assertEquals(c, qubit3.get(0))
        assertEquals(c, qubit3.get(1))
    }
}

