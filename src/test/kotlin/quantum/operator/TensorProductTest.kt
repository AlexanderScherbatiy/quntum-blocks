package quantum.operator

import org.junit.Test
import quantum.blocks.Complex.Companion.Zero
import quantum.blocks.Complex.Companion.complex
import quantum.blocks.InverseSqrt2
import quantum.blocks.hadamar
import quantum.blocks.identity
import kotlin.test.assertEquals

class TensorProductTest {

    /**
     * ( 1  0 )  tensor   ( 1  1 )
     * ( 0  1 )  product  ( 1 -1 ) / sqrt(2)
     */
    @Test
    fun tensorProductIdentityHadamarTest() {


        val result = identity().tensorProduct(hadamar())

        assertEquals(4, result.rows)
        assertEquals(4, result.columns)

        assertEquals(InverseSqrt2, result.get(0, 0))
        assertEquals(InverseSqrt2, result.get(0, 1))
        assertEquals(InverseSqrt2, result.get(1, 0))
        assertEquals(-InverseSqrt2, result.get(1, 1))

        assertEquals(Zero, result.get(0, 2))
        assertEquals(Zero, result.get(0, 3))
        assertEquals(Zero, result.get(1, 2))
        assertEquals(Zero, result.get(1, 3))

        assertEquals(Zero, result.get(2, 0))
        assertEquals(Zero, result.get(2, 1))
        assertEquals(Zero, result.get(3, 0))
        assertEquals(Zero, result.get(3, 1))

        assertEquals(InverseSqrt2, result.get(2, 2))
        assertEquals(InverseSqrt2, result.get(2, 3))
        assertEquals(InverseSqrt2, result.get(3, 2))
        assertEquals(-InverseSqrt2, result.get(3, 3))
    }
}