package quantum.gate

import org.junit.Test
import quantum.core.Complex
import quantum.core.Complex.Companion.complex
import quantum.core.Qubit
import quantum.util.assertComplexEquals
import quantum.util.assertMatrixIndexedValueIteratorEquals
import quantum.util.assertStateEquals
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

    @Test
    fun testProductState() {

        val hadamar = hadamar()
        assertStateEquals(Qubit.Plus, hadamar * Qubit.Zero)
        assertStateEquals(Qubit.Minus, hadamar * Qubit.One)
        assertStateEquals(Qubit.Zero, hadamar * Qubit.Plus)
        assertStateEquals(Qubit.One, hadamar * Qubit.Minus)
    }


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
    fun testProductGate() {
        val result = hadamar() * hadamar()

        assertEquals(2, result.size)
        assertEquals(2, result.size)

        assertComplexEquals(Complex.One, result[0, 0])
        assertComplexEquals(Complex.Zero, result[0, 1])
        assertComplexEquals(Complex.Zero, result[1, 0])
        assertComplexEquals(Complex.One, result[1, 1])
    }

    @Test
    fun rowIndexedValueIterator() {
        assertMatrixIndexedValueIteratorEquals(
                hadamar().rowsIndexedValueIterator(),
                arrayOf(
                        intArrayOf(0, 1),
                        intArrayOf(0, 1)
                ),
                arrayOf(
                        arrayOf(inverseSqrt2, inverseSqrt2),
                        arrayOf(inverseSqrt2, -inverseSqrt2)
                )
        )
    }
}