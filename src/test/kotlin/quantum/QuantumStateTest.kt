package quantum

import kotlin.test.assertEquals
import org.junit.Test
import quantum.blocks.*

class QuantumStateTest {

    @Test
    fun testQuantumStateSize1() {

        val quantumState1 = quantumState(arrayListOf(ComplexOne))
        assertEquals(1, quantumState1.size)
        assertEquals(ComplexOne, quantumState1.get(0))

        val quantumState2 = quantumState(arrayListOf(Complex(3.0)))
        assertEquals(1, quantumState2.size)
        assertEquals(ComplexOne, quantumState2.get(0))

        val quantumState3 = quantumState(arrayListOf(Complex(0.0, 5.0)))
        assertEquals(1, quantumState3.size)
        assertEquals(ComplexI, quantumState3.get(0))
    }


    @Test
    fun testTensorProduct1() {

        val quantumState = tensorProduct(listOf(
                Qubit.from(ComplexOne, ComplexZero),
                Qubit.from(ComplexOne, ComplexZero)
        ))

        assertEquals(4, quantumState.size)
        assertEquals(ComplexOne, quantumState.get(0))
        assertEquals(ComplexZero, quantumState.get(1))
        assertEquals(ComplexZero, quantumState.get(2))
        assertEquals(ComplexZero, quantumState.get(3))
    }

    @Test
    fun testTensorProduct2() {

        val quantumState = tensorProduct(listOf(
                Qubit.from(ComplexZero, ComplexOne),
                Qubit.from(ComplexZero, ComplexOne)
        ))

        assertEquals(4, quantumState.size)
        assertEquals(ComplexZero, quantumState.get(0))
        assertEquals(ComplexZero, quantumState.get(1))
        assertEquals(ComplexZero, quantumState.get(2))
        assertEquals(ComplexOne, quantumState.get(3))
    }

    @Test
    fun testTensorProduct3() {

        val quantumState = tensorProduct(listOf(
                Qubit.from(ComplexOne, ComplexZero),
                Qubit.from(ComplexZero, ComplexOne)
        ))

        assertEquals(4, quantumState.size)
        assertEquals(ComplexZero, quantumState.get(0))
        assertEquals(ComplexZero, quantumState.get(1))
        assertEquals(ComplexOne, quantumState.get(2))
        assertEquals(ComplexZero, quantumState.get(3))
    }
}

