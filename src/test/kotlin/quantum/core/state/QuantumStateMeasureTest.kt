package quantum.core.state

import org.junit.Test
import quantum.core.Complex
import quantum.core.Qubit
import quantum.core.measureIndex
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class QuantumStateMeasureTest {

    @Test
    fun testMeasureStandardBasis() {
        assertEquals(0, Qubit.Zero.measureIndex())
        assertEquals(1, Qubit.One.measureIndex())
    }

    @Test
    fun testMeasurePlusMinusBasis() {

        val i = Complex.I
        val e = Complex.One

        testMeasurePlusMinusBasis(Qubit.Plus)
        testMeasurePlusMinusBasis(Qubit.Minus)
        testMeasurePlusMinusBasis(Qubit.from(e, i))
        testMeasurePlusMinusBasis(Qubit.from(-i, e))
        testMeasurePlusMinusBasis(Qubit.from(i, -i))
    }

    private fun testMeasurePlusMinusBasis(qubit: Qubit) {

        val n = 100000
        val count = arrayOf(0, 0)

        for (i in 0 until n) {
            count[qubit.measureIndex()]++
        }

        val delta = Math.abs(count[0] - count[1])
        assertTrue { delta < 0.01 * n }
    }
}