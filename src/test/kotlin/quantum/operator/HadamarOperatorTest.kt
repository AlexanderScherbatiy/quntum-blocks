package quantum.operator

import org.junit.Test
import quantum.blocks.Complex.Companion.complex
import quantum.blocks.hadamar
import quantum.junit.assertComplexEquals
import kotlin.math.sqrt
import kotlin.test.assertEquals


class HadamarOperatorTest {

    val inverseSqrt2 = complex( 1.0 / sqrt(2.0), 0.0)

    @Test
    fun testElements() {

        val hadamar = hadamar()

        assertEquals(2, hadamar.rows)
        assertEquals(2, hadamar.columns)
        assertComplexEquals(inverseSqrt2, hadamar.get(0, 0))
        assertComplexEquals(inverseSqrt2, hadamar.get(0, 1))
        assertComplexEquals(inverseSqrt2, hadamar.get(1, 0))
        assertComplexEquals(-inverseSqrt2, hadamar.get(1, 1))
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testRowOutOfBounds() {
        hadamar().get(2, 0)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testColumnOutOfBounds() {
        hadamar().get(0, 2)
    }
}