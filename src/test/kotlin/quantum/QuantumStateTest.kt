package quantum

import quantum.blocks.Complex

import kotlin.test.assertEquals
import org.junit.Test
import quantum.blocks.Qubit
import quantum.blocks.quantumState
import quantum.blocks.tensorProduct

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


    @Test
    fun testTensorProduct1() {

        val quantumState = tensorProduct(listOf(
                Qubit.from(Complex.ONE, Complex.ZERO),
                Qubit.from(Complex.ONE, Complex.ZERO)
        ))

        assertEquals(4, quantumState.size)
        assertEquals(Complex.ONE, quantumState.get(0))
        assertEquals(Complex.ZERO, quantumState.get(1))
        assertEquals(Complex.ZERO, quantumState.get(2))
        assertEquals(Complex.ZERO, quantumState.get(3))
    }

    @Test
    fun testTensorProduct2() {

        val quantumState = tensorProduct(listOf(
                Qubit.from(Complex.ZERO, Complex.ONE),
                Qubit.from(Complex.ZERO, Complex.ONE)
        ))

        assertEquals(4, quantumState.size)
        assertEquals(Complex.ZERO, quantumState.get(0))
        assertEquals(Complex.ZERO, quantumState.get(1))
        assertEquals(Complex.ZERO, quantumState.get(2))
        assertEquals(Complex.ONE, quantumState.get(3))
    }

    @Test
    fun testTensorProduct3() {

        val quantumState = tensorProduct(listOf(
                Qubit.from(Complex.ONE, Complex.ZERO),
                Qubit.from(Complex.ZERO, Complex.ONE)
        ))

        assertEquals(4, quantumState.size)
        assertEquals(Complex.ZERO, quantumState.get(0))
        assertEquals(Complex.ZERO, quantumState.get(1))
        assertEquals(Complex.ONE, quantumState.get(2))
        assertEquals(Complex.ZERO, quantumState.get(3))
    }
}

