package quantum.state

import org.junit.Test
import quantum.core.Complex.Companion.I
import quantum.core.Complex.Companion.One
import quantum.core.Complex.Companion.complex
import quantum.core.quantumState
import quantum.junit.assertComplexEquals
import kotlin.test.assertEquals

class QuantumStateTest {

    val sqrt2 = kotlin.math.sqrt(2.0)

    @Test
    fun testQuantumStateSize1() {

        val quantumState1 = quantumState(One)
        assertEquals(1, quantumState1.size)
        assertEquals(One, quantumState1.get(0))

        val quantumState2 = quantumState(complex(3.0))
        assertEquals(1, quantumState2.size)
        assertEquals(One, quantumState2.get(0))

        val quantumState3 = quantumState(complex(0.0, 5.0))
        assertEquals(1, quantumState3.size)
        assertEquals(I, quantumState3.get(0))
    }

    @Test
    fun testQuantumStateSize2() {

        val quantumState1 = quantumState(One, One)
        assertEquals(2, quantumState1.size)
        assertComplexEquals(complex(1.0 / sqrt2), quantumState1[0])
        assertComplexEquals(complex(1.0 / sqrt2), quantumState1[1])

        val quantumState2 = quantumState(One, I)
        assertEquals(2, quantumState2.size)
        assertComplexEquals(complex(1.0 / sqrt2), quantumState2[0])
        assertComplexEquals(complex(0.0, 1.0 / sqrt2), quantumState2[1])
    }
}

