package quantum.core.state

import org.junit.Test
import quantum.core.Complex
import quantum.core.Complex.Companion.complex
import quantum.core.Qubit
import quantum.util.assertComplexEquals
import kotlin.test.assertEquals

class QuantumStateTensorProductTest {

    val OneHalf = complex(0.5)

    /**
     * (1, 0) * (1, 0) = (1, 0, 0, 0)
     */
    @Test
    fun testTensorProductZeroZero() {

        val quantumState = Qubit.Zero tensor Qubit.Zero

        assertEquals(4, quantumState.size)
        assertComplexEquals(Complex.One, quantumState[0])
        assertComplexEquals(Complex.Zero, quantumState[2])
        assertComplexEquals(Complex.Zero, quantumState[3])
        assertComplexEquals(Complex.Zero, quantumState[1])
    }

    /**
     * (1, 0) * (0, 1) = (0, 1, 0, 0)
     */
    @Test
    fun testTensorProductZeroOne() {

        val quantumState = Qubit.Zero tensor Qubit.One

        assertEquals(4, quantumState.size)
        assertComplexEquals(Complex.Zero, quantumState[0])
        assertComplexEquals(Complex.One, quantumState[1])
        assertComplexEquals(Complex.Zero, quantumState[2])
        assertComplexEquals(Complex.Zero, quantumState[3])
    }

    /**
     * (0, 1) * (0, 1) = (0, 0, 0, 1)
     */
    @Test
    fun testTensorProductOneOne() {

        val quantumState = Qubit.One tensor Qubit.One

        assertEquals(4, quantumState.size)
        assertComplexEquals(Complex.Zero, quantumState[0])
        assertComplexEquals(Complex.Zero, quantumState[1])
        assertComplexEquals(Complex.Zero, quantumState[2])
        assertComplexEquals(Complex.One, quantumState[3])
    }

    /**
     * 1/sqrt(2) (1, 1) * 1/sqrt(2) (1, -1) =
     * 1/2 (1, -1, 1, -1)
     */
    @Test
    fun testTensorPlusMinus() {
        val quantumState = Qubit.Plus tensor Qubit.Minus

        assertEquals(4, quantumState.size)
        assertComplexEquals(OneHalf, quantumState[0])
        assertComplexEquals(-OneHalf, quantumState[1])
        assertComplexEquals(OneHalf, quantumState[2])
        assertComplexEquals(-OneHalf, quantumState[3])
    }
}