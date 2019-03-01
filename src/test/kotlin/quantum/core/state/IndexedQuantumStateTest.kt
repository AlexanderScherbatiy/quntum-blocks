package quantum.core.state

import org.junit.Test
import quantum.core.Complex
import quantum.core.quantumState
import quantum.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

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

        assertComplexIndexedValueIteratorEquals(
                indexedQuantumState.indexedValueIterator(),
                intArrayOf(0, 3),
                arrayOf(e / sqrt2, e / sqrt2)
        )

        assertStateEquals(quantumState(e, z, z, e), indexedQuantumState)
        assertStateEquals(indexedQuantumState, quantumState(e, z, z, e))
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


    @Test
    fun testEquals() {
        assertEquals(
                quantumState(1, intArrayOf(0), arrayOf(e)),
                quantumState(1, intArrayOf(0), arrayOf(e))
        )

        assertEquals(
                quantumState(1, intArrayOf(1), arrayOf(e)),
                quantumState(1, intArrayOf(1), arrayOf(e))
        )

        assertNotEquals(
                quantumState(1, intArrayOf(0), arrayOf(e)),
                quantumState(1, intArrayOf(1), arrayOf(e))
        )

        assertEquals(
                quantumState(2, intArrayOf(0, 1), arrayOf(e, i)),
                quantumState(2, intArrayOf(0, 1), arrayOf(e, i))
        )

        assertNotEquals(
                quantumState(2, intArrayOf(0, 1), arrayOf(e, i)),
                quantumState(2, intArrayOf(0, 1), arrayOf(i, e))
        )

        assertEquals(
                quantumState(4, intArrayOf(0, 3), arrayOf(e, e)),
                quantumState(4, intArrayOf(0, 3), arrayOf(e, e))
        )

        assertNotEquals(
                quantumState(4, intArrayOf(0, 3), arrayOf(e, e)),
                quantumState(4, intArrayOf(0, 2), arrayOf(e, e))
        )
    }

    @Test
    fun testHashCode() {

        assertHashEquals(quantumState(1, intArrayOf(0), arrayOf(e)), e)
        assertHashEquals(quantumState(1, intArrayOf(0), arrayOf(i)), i)
        assertHashNotEquals(quantumState(1, intArrayOf(0), arrayOf(i)), e)

        assertHashEquals(quantumState(2, intArrayOf(0), arrayOf(e)), e)
        assertHashEquals(quantumState(2, intArrayOf(0), arrayOf(i)), i)
        assertHashNotEquals(quantumState(2, intArrayOf(0), arrayOf(i)), e)

        assertHashEquals(quantumState(2, intArrayOf(1), arrayOf(e)), e)
        assertHashEquals(quantumState(2, intArrayOf(1), arrayOf(i)), i)
        assertHashNotEquals(quantumState(2, intArrayOf(1), arrayOf(i)), e)

        assertHashEquals(quantumState(2, intArrayOf(0, 1), arrayOf(e, i)), e, i)
        assertHashNotEquals(quantumState(2, intArrayOf(0, 1), arrayOf(e, i)), i, e)
    }
}