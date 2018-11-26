package quantum.core.gate

import org.junit.Test
import quantum.core.Complex.Companion.One
import quantum.core.Complex.Companion.Zero
import quantum.core.OneHalf
import quantum.gate.hadamar
import quantum.junit.assertComplexEquals
import kotlin.test.assertEquals

class ProductTest {

    /**
     *  hadamar x hadamar=
     *  1 / 2 *
     * ( 1  1 ) x ( 1  1 )
     * ( 1 -1 )   ( 1 -1 )
     *  =
     * ( 1  0 )
     * ( 0  1 )
     */
    @Test
    fun testHadamarHadamarProduct() {
        val result = hadamar() * hadamar()

        assertEquals(2, result.rows)
        assertEquals(2, result.columns)

        assertComplexEquals(One, result[0, 0])
        assertComplexEquals(Zero, result[0, 1])
        assertComplexEquals(Zero, result[1, 0])
        assertComplexEquals(One, result[1, 1])
    }
}