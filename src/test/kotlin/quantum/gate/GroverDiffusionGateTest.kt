package quantum.gate

import org.junit.Test
import quantum.core.Bit
import quantum.core.Complex
import quantum.util.assertComplexEquals
import kotlin.test.assertEquals

/**
 * GroverDiffusionGate
 *  U = 2 |+><+| - I
 *  |+> =(|0>+|1>)^n / sqrt(2)^n
 */
class GroverDiffusionGateTest {


    /**
     * U = 2 |+><+| - I
     * |+> =(|0>+|1>) / sqrt(2)
     *
     * (-1/2   1/2)
     * ( 1/2  -1/2)
     */
    @Test
    fun testGroverDiffusionGate2x2() {

        val U = diffusion(2)

        val half = Complex.One / 2.0
        assertEquals(2, U.size)

        assertComplexEquals(-half, U[0, 0])
        assertComplexEquals(half, U[0, 1])
        assertComplexEquals(half, U[1, 0])
        assertComplexEquals(-half, U[1, 1])
    }

    /**
     * U = 2 |+><+| - I
     * |+> =(|00>+|01>+|10>+|11>) / 2
     *
     * (-1/4   1/4  1/4  1/4)
     * ( 1/4  -1/4  1/4  1/4)
     * ( 1/4   1/4 -1/4  1/4)
     * ( 1/4   1/4  1/4 -1/4)
     */

    @Test
    fun testGroverDiffusionGate4x4() {

        val U = diffusion(4)

        val elem = Complex.One / 4.0
        val diag = elem - 1.0
        assertEquals(4, U.size)

        assertComplexEquals(diag, U[0, 0])
        assertComplexEquals(elem, U[0, 1])
        assertComplexEquals(elem, U[1, 0])
        assertComplexEquals(diag, U[1, 1])
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testRowOutOfBounds() {
        diffusion(2)[2, 0]
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testColumnOutOfBounds() {
        diffusion(2)[0, 2]
    }


}