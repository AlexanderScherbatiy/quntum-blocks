package quantum.core.state

import org.junit.Test
import quantum.core.Complex
import quantum.core.quantumState
import quantum.junit.assertComplexEquals
import kotlin.test.assertEquals

class IndexedQuantumStateTest {

    val sqrt2 = Math.sqrt(2.0)
    val z = Complex.Zero
    val e = Complex.One
        val i = Complex.I


    @Test
    fun testConstructor() {

        // state (|00>+|11>) / sqrt(2)
        val indexedQuantumState = quantumState(4,
                intArrayOf(0, 3), arrayOf(e, e))

        assertComplexEquals(e / sqrt2, indexedQuantumState[0])
        assertComplexEquals(z, indexedQuantumState[1])
        assertComplexEquals(z, indexedQuantumState[2])
        assertComplexEquals(e / sqrt2, indexedQuantumState[3])

        assertEquals(quantumState(e, z, z, e), indexedQuantumState)
        assertEquals(indexedQuantumState, quantumState(e, z, z, e))
    }

    @Test
    fun testScalarProduct() {

        // state (|00>+|11>) / sqrt(2)
        val indexedQuantumState = quantumState(4,
                intArrayOf(0, 3), arrayOf(e, e))

        assertComplexEquals(e, indexedQuantumState scalar quantumState(e, z, z, e))
        assertComplexEquals(e, quantumState(e, z, z, e) scalar indexedQuantumState)

        assertComplexEquals(e / sqrt2, indexedQuantumState scalar quantumState(e, z, z, z))
        assertComplexEquals(e / sqrt2, quantumState(e, z, z, z) scalar indexedQuantumState)

        assertComplexEquals(z, indexedQuantumState scalar quantumState(z, e, e, z))
        assertComplexEquals(z, quantumState(z, e, e, z) scalar indexedQuantumState)
    }
}