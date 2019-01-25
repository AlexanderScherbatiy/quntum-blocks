package quantum.core.state

import org.junit.Test
import quantum.core.Complex.Companion.I
import quantum.core.Complex.Companion.One
import quantum.core.Complex.Companion.Zero
import quantum.core.Complex.Companion.complex
import quantum.core.normalize
import quantum.core.quantumState
import quantum.junit.assertComplexEquals
import quantum.junit.testQuantumStateHash
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class QuantumStateTest {

    val sqrt2 = kotlin.math.sqrt(2.0)

    @Test
    fun testQuantumStateSize1() {

        val quantumState1 = quantumState(One)
        assertEquals(1, quantumState1.size)
        assertEquals(One, quantumState1[0])

        val quantumState2 = quantumState(complex(3.0))
        assertEquals(1, quantumState2.size)
        assertEquals(One, quantumState2[0])

        val quantumState3 = quantumState(complex(0.0, 5.0))
        assertEquals(1, quantumState3.size)
        assertEquals(I, quantumState3[0])
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

    @Test
    fun testEquals() {

        assertEquals(quantumState(I), quantumState(I))
        assertEquals(quantumState(One), quantumState(One))
        assertNotEquals(quantumState(I), quantumState(One))

        assertEquals(quantumState(Zero, One), quantumState(Zero, One))
        assertEquals(quantumState(One, Zero), quantumState(One, Zero))
        assertEquals(quantumState(One, One), quantumState(One, One))
        assertNotEquals(quantumState(Zero, One), quantumState(One, One))

        assertEquals(quantumState(complex(1.2, 3.4)), quantumState(complex(1.2, 3.4)))
        assertNotEquals(quantumState(complex(1.2, 3.4)), quantumState(complex(1.2, 4.4)))

        assertEquals(
                quantumState(Zero, complex(5.6, 7.8)),
                quantumState(Zero, complex(5.6, 7.8))
        )
        assertEquals(
                quantumState(complex(1.2, 3.4), Zero),
                quantumState(complex(1.2, 3.4), Zero)
        )
        assertEquals(
                quantumState(complex(1.2, 3.4), complex(5.6, 7.8)),
                quantumState(complex(1.2, 3.4), complex(5.6, 7.8))
        )
    }

    @Test
    fun testHashCode() {

        assertEquals(testQuantumStateHash(I), quantumState(I).hashCode())
        assertEquals(testQuantumStateHash(One), quantumState(One).hashCode())
        assertNotEquals(testQuantumStateHash(I), quantumState(One).hashCode())

        assertEquals(testQuantumStateHash(One), quantumState(Zero, One).hashCode())
        assertEquals(testQuantumStateHash(One), quantumState(One, Zero).hashCode())

        assertEquals(
                testQuantumStateHash(complex(1.2, 3.4)),
                quantumState(complex(1.2, 3.4)).hashCode()
        )

        assertEquals(
                testQuantumStateHash(complex(1.2, 3.4), complex(5.6, 7.8)),
                quantumState(complex(1.2, 3.4), complex(5.6, 7.8)).hashCode()
        )

        assertEquals(
                testQuantumStateHash(complex(5.6, 7.8)),
                quantumState(Zero, complex(5.6, 7.8)).hashCode()
        )

        assertEquals(
                testQuantumStateHash(complex(1.2, 3.4)),
                quantumState(complex(1.2, 3.4), Zero).hashCode()
        )


        val coefficients = normalize(complex(1.2, 3.4), complex(5.6, 7.8))

        assertEquals(
                testQuantumStateHash(*coefficients),
                quantumState(*coefficients).hashCode())
    }

}

