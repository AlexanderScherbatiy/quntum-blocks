package quantum.core.state

import org.junit.Test
import quantum.core.Complex
import quantum.core.Complex.Companion.complex
import quantum.core.Qubit
import quantum.core.quantumState
import quantum.core.qubit
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class MixedQuantumStateTest {


    @Test
    fun testEquals() {

        assertEquals(Qubit.Zero, quantumState(Complex.One, Complex.Zero))
        assertEquals(Qubit.Zero, quantumState(2, intArrayOf(0), arrayOf(Complex.One)))
        assertEquals(
                quantumState(Complex.One, Complex.Zero),
                quantumState(2, intArrayOf(0), arrayOf(Complex.One))
        )

        assertEquals(Qubit.One, quantumState(Complex.Zero, Complex.One))
        assertEquals(Qubit.One, quantumState(2, intArrayOf(1), arrayOf(Complex.One)))
        assertEquals(
                quantumState(Complex.Zero, Complex.One),
                quantumState(2, intArrayOf(1), arrayOf(Complex.One))
        )

        assertNotEquals(Qubit.Zero, quantumState(Complex.One))

        assertEquals(quantumState(Complex.One, Complex.Zero), Qubit.Zero)
        assertEquals(quantumState(2, intArrayOf(0), arrayOf(Complex.One)), Qubit.Zero)
        assertEquals(
                quantumState(2, intArrayOf(0), arrayOf(Complex.One)),
                quantumState(Complex.One, Complex.Zero)
        )

        assertEquals(quantumState(Complex.Zero, Complex.One), Qubit.One)
        assertEquals(quantumState(2, intArrayOf(1), arrayOf(Complex.One)), Qubit.One)
        assertEquals(
                quantumState(2, intArrayOf(1), arrayOf(Complex.One)),
                quantumState(Complex.Zero, Complex.One)
        )

        assertNotEquals(quantumState(Complex.One), Qubit.Zero)

        assertEquals(
                qubit(complex(1.2, 3.4), complex(5.6, 7.8)),
                quantumState(complex(1.2, 3.4), complex(5.6, 7.8))
        )

        assertEquals(
                quantumState(complex(1.2, 3.4), complex(5.6, 7.8)),
                qubit(complex(1.2, 3.4), complex(5.6, 7.8))
        )

        assertEquals(
                quantumState(2, intArrayOf(0, 1), arrayOf(complex(1.2, 3.4), complex(5.6, 7.8))),
                qubit(complex(1.2, 3.4), complex(5.6, 7.8))
        )

        assertEquals(
                quantumState(2, intArrayOf(0, 1), arrayOf(complex(1.2, 3.4), complex(5.6, 7.8))),
                quantumState(complex(1.2, 3.4), complex(5.6, 7.8))
        )
    }

    @Test
    fun testHashCode() {

        assertEquals(Qubit.Zero.hashCode(), quantumState(Complex.One).hashCode())
        assertEquals(Qubit.One.hashCode(), quantumState(Complex.Zero, Complex.One).hashCode())

        assertEquals(
                qubit(complex(1.2, 3.4), complex(5.6, 7.8)).hashCode(),
                quantumState(complex(1.2, 3.4), complex(5.6, 7.8)).hashCode()
        )
    }
}