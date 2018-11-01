package quantum

import quantum.blocks.Complex

import kotlin.test.assertEquals
import org.junit.Test
import quantum.blocks.quantumState

class QuantumStateTest {

    @Test
    fun testQuantumStateSize1() {

        val quantumState1 = quantumState(arrayListOf(Complex.ONE))
        assertEquals(1, quantumState1.size)
        assertEquals(Complex.ONE, quantumState1.get(0))

        val quantumState2 = quantumState(arrayListOf(Complex(3, 0)))
        assertEquals(1, quantumState2.size)
        assertEquals(Complex.ONE, quantumState2.get(0))

        val quantumState3 = quantumState(arrayListOf(Complex(0, 5)))
        assertEquals(1, quantumState3.size)
        assertEquals(Complex.I, quantumState3.get(0))
    }
}

