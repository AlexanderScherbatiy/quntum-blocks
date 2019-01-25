package quantum.core.state

import org.junit.Test
import quantum.core.Complex
import quantum.core.Complex.Companion.One
import quantum.core.Complex.Companion.Zero
import quantum.core.Complex.Companion.complex
import quantum.core.Qubit
import quantum.core.normalize
import quantum.core.qubit
import quantum.junit.testQuantumStateHash
import kotlin.math.sqrt
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class QubitTest {

    val sqrt2 = kotlin.math.sqrt(2.0)

    @Test
    fun testConstant() {
        assertEquals(One, Qubit.Zero.zero)
        assertEquals(Zero, Qubit.Zero.one)

        assertEquals(Zero, Qubit.One.zero)
        assertEquals(One, Qubit.One.one)

        assertEquals(complex(1.0 / sqrt2), Qubit.Plus.zero)
        assertEquals(complex(1.0 / sqrt2), Qubit.Plus.one)

        assertEquals(complex(1.0 / sqrt2), Qubit.Minus.zero)
        assertEquals(complex(-1.0 / sqrt2), Qubit.Minus.one)
    }

    @Test
    fun testFromComplex() {

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
        assertEquals(c, qubit3[0])
        assertEquals(c, qubit3[1])
    }

    @Test
    fun testEquals() {

        assertEquals(Qubit.Zero, Qubit.Zero)
        assertEquals(Qubit.Zero, qubit(Complex.One, Complex.Zero))

        assertEquals(Qubit.One, Qubit.One)
        assertEquals(Qubit.One, qubit(Complex.Zero, Complex.One))

        assertNotEquals(Qubit.Zero, Qubit.One)

        assertEquals(
                qubit(complex(1.2, 2.3), complex(3.4, 4.5)),
                qubit(complex(1.2, 2.3), complex(3.4, 4.5))
        )

        assertNotEquals(
                qubit(complex(1.2, 2.3), complex(3.4, 4.5)),
                qubit(complex(2.2, 2.3), complex(3.4, 4.5))
        )

        assertNotEquals(
                qubit(complex(1.2, 2.3), complex(3.4, 4.5)),
                qubit(complex(2.2, 2.3), complex(3.4, 5.5))
        )
    }

    @Test
    fun testHashCode() {

        assertEquals(testQuantumStateHash(Complex.One), Qubit.Zero.hashCode())
        assertEquals(testQuantumStateHash(Complex.One), Qubit.One.hashCode())

        val coefficients = normalize(complex(1.2, 3.4), complex(5.6, 7.8))

        assertEquals(
                testQuantumStateHash(*coefficients),
                qubit(coefficients[0], coefficients[1]).hashCode())
    }
}
