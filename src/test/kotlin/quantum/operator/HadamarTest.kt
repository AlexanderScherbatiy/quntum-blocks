package quantum.operator

import org.junit.Test
import quantum.blocks.hadamar
import kotlin.test.assertEquals


class HadamarTest {

    val inverseSqrt2: Double = 1.0 / kotlin.math.sqrt(2.0)

    @Test
    fun testElements() {

        val hadamar = hadamar()

        assertEquals(2, hadamar.rows)
        assertEquals(2, hadamar.columns)
        assertEquals(inverseSqrt2, hadamar.get(0, 0))
        assertEquals(inverseSqrt2, hadamar.get(0, 1))
        assertEquals(inverseSqrt2, hadamar.get(1, 0))
        assertEquals(-inverseSqrt2, hadamar.get(1, 1))
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