package quantum.core.state

import org.junit.Test
import quantum.core.Complex
import quantum.core.Qubit
import quantum.core.measureBasisIndex
import quantum.core.quantumState
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class QuantumStateMeasureTest {

    private val i = Complex.I
    private val z = Complex.Zero
    private val e = Complex.One

    @Test
    fun testMeasureStandardBasis() {
        assertEquals(0, Qubit.Zero.measureBasisIndex())
        assertEquals(1, Qubit.One.measureBasisIndex())

        assertEquals(0, quantumState(e).measureBasisIndex())
        assertEquals(0, quantumState(i).measureBasisIndex())

        assertEquals(0, quantumState(e, z, z).measureBasisIndex())
        assertEquals(0, quantumState(i, z, z).measureBasisIndex())
        assertEquals(1, quantumState(z, e, z).measureBasisIndex())
        assertEquals(1, quantumState(z, i, z).measureBasisIndex())
        assertEquals(2, quantumState(z, z, e).measureBasisIndex())
        assertEquals(2, quantumState(z, z, i + e).measureBasisIndex())
    }

    @Test
    fun testMeasurePlusMinusBasis() {


        testMeasurePlusMinusBasis(Qubit.Plus)
        testMeasurePlusMinusBasis(Qubit.Minus)
        testMeasurePlusMinusBasis(Qubit.from(e, i))
        testMeasurePlusMinusBasis(Qubit.from(-i, e))
        testMeasurePlusMinusBasis(Qubit.from(i, -i))
    }

    private fun testMeasurePlusMinusBasis(qubit: Qubit) {

        val n = 100000
        val threshold = 0.01
        val count = arrayOf(0, 0)

        for (i in 0 until n) {
            count[qubit.measureBasisIndex()]++
        }

        val delta = Math.abs(count[0] - count[1])
        assertTrue("n: $n, delta: $delta, threshold: $threshold") {
            delta.toDouble() / n.toDouble() < threshold
        }
    }
}