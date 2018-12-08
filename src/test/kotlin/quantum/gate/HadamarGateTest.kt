package quantum.gate

import org.junit.Test
import quantum.core.Complex.Companion.complex
import quantum.junit.assertComplexEquals
import kotlin.math.sqrt
import kotlin.test.assertEquals


class HadamarGateTest {

    private val inverseSqrt2 = complex(1.0 / sqrt(2.0), 0.0)

    @Test
    fun testElements() {

        val hadamar = hadamar()

        assertEquals(2, hadamar.size)

        assertComplexEquals(inverseSqrt2, hadamar[0, 0])
        assertComplexEquals(inverseSqrt2, hadamar[0, 1])
        assertComplexEquals(inverseSqrt2, hadamar[1, 0])
        assertComplexEquals(-inverseSqrt2, hadamar[1, 1])
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testRowOutOfBounds() {
        hadamar()[2, 0]
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testColumnOutOfBounds() {
        hadamar()[0, 2]
    }
}