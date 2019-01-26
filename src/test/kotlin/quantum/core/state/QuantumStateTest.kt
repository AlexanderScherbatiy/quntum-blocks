package quantum.core.state

import org.junit.Test
import quantum.core.Complex.Companion.I
import quantum.core.Complex.Companion.One
import quantum.core.Complex.Companion.Zero
import quantum.core.Complex.Companion.complex
import quantum.core.normalize
import quantum.core.quantumState
import quantum.junit.assertComplexEquals
import quantum.junit.assertHashEquals
import quantum.junit.assertHashNotEquals
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

        assertHashEquals(quantumState(I), I)
        assertHashEquals(quantumState(One), One)
        assertHashNotEquals(quantumState(I), One)

        assertHashEquals(quantumState(Zero, I), I)
        assertHashEquals(quantumState(Zero, One), One)
        assertHashNotEquals(quantumState(Zero, One), Zero, One)

        assertHashEquals(quantumState(complex(1.2, 3.4)), complex(1.2, 3.4))

        assertHashEquals(
                quantumState(complex(1.2, 3.4), complex(5.6, 7.8)),
                complex(1.2, 3.4), complex(5.6, 7.8)
        )

        assertHashEquals(
                quantumState(complex(1.2, 3.4), complex(5.6, 7.8)),
                complex(1.2, 3.4), complex(5.6, 7.8)
        )

        assertHashEquals(
                quantumState(complex(1.2, 3.4), Zero),
                complex(1.2, 3.4)
        )

        assertHashEquals(
                quantumState(Zero, complex(5.6, 7.8)),
                complex(5.6, 7.8)
        )

        val coefficients = normalize(complex(1.2, 3.4), complex(5.6, 7.8))
        assertHashEquals(quantumState(*coefficients), *coefficients.copyOf())
    }

}

