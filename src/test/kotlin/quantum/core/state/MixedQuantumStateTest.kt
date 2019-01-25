package quantum.core.state

import org.junit.Test
import quantum.core.Complex
import quantum.core.Complex.Companion.complex
import quantum.core.Qubit
import quantum.core.quantumState
import quantum.core.qubit
import kotlin.test.assertEquals

class MixedQuantumStateTest {


    @Test
    fun testEquals() {

        assertEquals(Qubit.Zero, quantumState(Complex.One))
        assertEquals(Qubit.One, quantumState(Complex.Zero, Complex.One))

        assertEquals(quantumState(Complex.One), Qubit.Zero)
        assertEquals(quantumState(Complex.Zero, Complex.One), Qubit.One)

        assertEquals(
                qubit(complex(1.2, 3.4), complex(5.6, 7.8)),
                quantumState(complex(1.2, 3.4), complex(5.6, 7.8))
        )

        assertEquals(
                quantumState(complex(1.2, 3.4), complex(5.6, 7.8)),
                qubit(complex(1.2, 3.4), complex(5.6, 7.8))
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