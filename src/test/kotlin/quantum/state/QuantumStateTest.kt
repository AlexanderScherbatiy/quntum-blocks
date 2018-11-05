package quantum.state

import org.junit.Test
import quantum.blocks.Complex.Companion.I
import quantum.blocks.Complex.Companion.One
import quantum.blocks.Complex.Companion.Zero
import quantum.blocks.Complex.Companion.complex
import quantum.blocks.Qubit
import quantum.blocks.quantumState
import quantum.blocks.tensorProduct
import kotlin.test.assertEquals

class QuantumStateTest {

    @Test
    fun testQuantumStateSize1() {

        val quantumState1 = quantumState(arrayListOf(One))
        assertEquals(1, quantumState1.size)
        assertEquals(One, quantumState1.get(0))

        val quantumState2 = quantumState(arrayListOf(complex(3.0)))
        assertEquals(1, quantumState2.size)
        assertEquals(One, quantumState2.get(0))

        val quantumState3 = quantumState(arrayListOf(complex(0.0, 5.0)))
        assertEquals(1, quantumState3.size)
        assertEquals(I, quantumState3.get(0))
    }


}

