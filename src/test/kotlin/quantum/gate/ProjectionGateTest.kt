package quantum.gate

import org.junit.Test
import quantum.core.Complex
import quantum.core.QuantumState
import quantum.core.quantumState
import quantum.core.toComplex
import quantum.junit.assertComplexEquals
import kotlin.test.assertEquals

class ProjectionGateTest {


    /**
     * (  1 ) * ( 1, i)
     * ( -i )
     *  =
     *  ( 1,  i )
     *  (-i,  1 )
     */
    @Test
    fun testProjectionGate() {
        val i = Complex.I
        val e = Complex.One
        val state = quantumState(e, i)
        val projection = projection(state)
        assertEquals(2, projection.rows)
        assertEquals(2, projection.columns)
        assertComplexEquals(e / 2.0, projection[0, 0])
        assertComplexEquals(i / 2.0, projection[0, 1])
        assertComplexEquals(-i / 2.0, projection[1, 0])
        assertComplexEquals(e / 2.0, projection[1, 1])
    }
}