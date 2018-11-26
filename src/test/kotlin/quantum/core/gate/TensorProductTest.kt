package quantum.core.gate

import org.junit.Test
import quantum.core.Complex.Companion.Zero
import quantum.core.InverseSqrt2
import quantum.core.OneHalf
import quantum.gate.hadamar
import quantum.gate.identity
import quantum.junit.assertComplexEquals
import kotlin.test.assertEquals

class TensorProductTest {

    /**
     * Identity x Hadamar =
     *  1 / sqrt(2) *
     * ( 1  0 ) x ( 1  1 )
     * ( 0  1 )   ( 1 -1 )
     */
    @Test
    fun testIdentityHadamarTensorProduct() {

        val result = identity() tensorProduct hadamar()

        assertEquals(4, result.rows)
        assertEquals(4, result.columns)

        assertComplexEquals(InverseSqrt2, result[0, 0])
        assertComplexEquals(InverseSqrt2, result[0, 1])
        assertComplexEquals(InverseSqrt2, result[1, 0])
        assertComplexEquals(-InverseSqrt2, result[1, 1])

        assertComplexEquals(Zero, result[0, 2])
        assertComplexEquals(Zero, result[0, 3])
        assertComplexEquals(Zero, result[1, 2])
        assertComplexEquals(Zero, result[1, 3])

        assertComplexEquals(Zero, result[2, 0])
        assertComplexEquals(Zero, result[2, 1])
        assertComplexEquals(Zero, result[3, 0])
        assertComplexEquals(Zero, result[3, 1])

        assertComplexEquals(InverseSqrt2, result[2, 2])
        assertComplexEquals(InverseSqrt2, result[2, 3])
        assertComplexEquals(InverseSqrt2, result[3, 2])
        assertComplexEquals(-InverseSqrt2, result[3, 3])
    }

    /**
     *  Hadamar x Identity=
     *  1 / sqrt(2) *
     * ( 1  1 ) x ( 1  0 )
     * ( 1 -1 )   ( 0  1 )
     */
    @Test
    fun testHadamarIdentityTensorProduct() {


        val result = hadamar() tensorProduct identity()

        assertEquals(4, result.rows)
        assertEquals(4, result.columns)

        assertComplexEquals(InverseSqrt2, result[0, 0])
        assertComplexEquals(Zero, result[0, 1])
        assertComplexEquals(Zero, result[1, 0])
        assertComplexEquals(InverseSqrt2, result[1, 1])

        assertComplexEquals(InverseSqrt2, result[0, 2])
        assertComplexEquals(Zero, result[0, 3])
        assertComplexEquals(Zero, result[1, 2])
        assertComplexEquals(InverseSqrt2, result[1, 3])

        assertComplexEquals(InverseSqrt2, result[2, 0])
        assertComplexEquals(Zero, result[2, 1])
        assertComplexEquals(Zero, result[3, 0])
        assertComplexEquals(InverseSqrt2, result[3, 1])

        assertComplexEquals(-InverseSqrt2, result[2, 2])
        assertComplexEquals(Zero, result[2, 3])
        assertComplexEquals(Zero, result[3, 2])
        assertComplexEquals(-InverseSqrt2, result[3, 3])
    }

    /**
     *  hadamar tesnsorProduct hadamar=
     *  1 / 2 *
     * ( 1  1 ) tp ( 1  1 )
     * ( 1 -1 )    ( 1 -1 )
     *  =
     * ( 1  1  1  1 )
     * ( 1 -1  1 -1 )
     * ( 1  1 -1 -1 )
     * ( 1 -1 -1  1 )
     */
    @Test
    fun testHadamarHadamarTensorProduct() {

        val result = hadamar() tensorProduct hadamar()

        assertEquals(4, result.rows)
        assertEquals(4, result.columns)

        assertComplexEquals(OneHalf, result[0, 0])
        assertComplexEquals(OneHalf, result[0, 1])
        assertComplexEquals(OneHalf, result[1, 0])
        assertComplexEquals(-OneHalf, result[1, 1])

        assertComplexEquals(OneHalf, result[0, 2])
        assertComplexEquals(OneHalf, result[0, 3])
        assertComplexEquals(OneHalf, result[1, 2])
        assertComplexEquals(-OneHalf, result[1, 3])

        assertComplexEquals(OneHalf, result[2, 0])
        assertComplexEquals(OneHalf, result[2, 1])
        assertComplexEquals(OneHalf, result[3, 0])
        assertComplexEquals(-OneHalf, result[3, 1])

        assertComplexEquals(-OneHalf, result[2, 2])
        assertComplexEquals(-OneHalf, result[2, 3])
        assertComplexEquals(-OneHalf, result[3, 2])
        assertComplexEquals(OneHalf, result[3, 3])
    }
}