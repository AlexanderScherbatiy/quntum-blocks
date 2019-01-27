package quantum.gate

import org.junit.Test
import quantum.core.*
import quantum.util.assertComplexEquals
import kotlin.test.assertEquals

class ProjectionGateTest {

    private val i = Complex.I
    private val e = Complex.One


    /**
     * projection((1, i))
     * (  1 ) * ( 1, i)
     * ( -i )
     *  =
     *  ( 1,  i )
     *  (-i,  1 )
     */
    @Test
    fun testProjectionGate1() {
        val state = quantumState(e, i)
        val projection = projection(state)

        assertEquals(2, projection.size)

        assertComplexEquals(e / 2.0, projection[0, 0])
        assertComplexEquals(i / 2.0, projection[0, 1])
        assertComplexEquals(-i / 2.0, projection[1, 0])
        assertComplexEquals(e / 2.0, projection[1, 1])
    }

    /**
     * projection((1, i), (i, -1))
     * (  1 ) * ( i, -1)
     * ( -i )
     *  =
     *  ( i, -1 )
     *  ( 1,  i )
     */
    @Test
    fun testProjectionGate2() {
        val state1 = quantumState(e, i)
        val state2 = quantumState(i, -e)
        val projection = projection(state1, state2)
        assertEquals(2, projection.size)
        assertEquals(2, projection.size)
        assertComplexEquals(i / 2.0, projection[0, 0])
        assertComplexEquals(-e / 2.0, projection[0, 1])
        assertComplexEquals(e / 2.0, projection[1, 0])
        assertComplexEquals(i / 2.0, projection[1, 1])
    }
}