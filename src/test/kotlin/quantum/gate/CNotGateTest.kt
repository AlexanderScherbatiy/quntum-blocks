package quantum.gate

import org.junit.Test
import quantum.core.Complex
import quantum.core.Qubit
import quantum.util.assertComplexEquals
import quantum.util.assertMatrixIndexedValueIteratorEquals
import quantum.util.assertStateEquals
import kotlin.test.assertEquals

class CNotGateTest {

    @Test
    fun testElements() {

        val cnot = cnot()

        assertEquals(4, cnot.size)

        assertComplexEquals(Complex.One, cnot[0, 0])
        assertComplexEquals(Complex.Zero, cnot[0, 1])
        assertComplexEquals(Complex.Zero, cnot[1, 0])
        assertComplexEquals(Complex.One, cnot[1, 1])

        assertComplexEquals(Complex.Zero, cnot[0, 2])
        assertComplexEquals(Complex.Zero, cnot[0, 3])
        assertComplexEquals(Complex.Zero, cnot[1, 2])
        assertComplexEquals(Complex.Zero, cnot[1, 3])

        assertComplexEquals(Complex.Zero, cnot[2, 0])
        assertComplexEquals(Complex.Zero, cnot[2, 1])
        assertComplexEquals(Complex.Zero, cnot[3, 0])
        assertComplexEquals(Complex.Zero, cnot[3, 1])

        assertComplexEquals(Complex.Zero, cnot[2, 2])
        assertComplexEquals(Complex.One, cnot[2, 3])
        assertComplexEquals(Complex.One, cnot[3, 2])
        assertComplexEquals(Complex.Zero, cnot[3, 3])
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testRowOutOfBounds() {
        cnot()[4, 3]
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testColumnOutOfBounds() {
        cnot()[2, 4]
    }

    @Test
    fun testProductState() {

        val cnot = cnot()
        val e1 = (Qubit.Zero tensor Qubit.Zero)
        val e2 = (Qubit.Zero tensor Qubit.One)
        val e3 = (Qubit.One tensor Qubit.Zero)
        val e4 = (Qubit.One tensor Qubit.One)

        assertStateEquals(e1, cnot * e1)
        assertStateEquals(e2, cnot * e2)
        assertStateEquals(e4, cnot * e3)
        assertStateEquals(e3, cnot * e4)
    }

    @Test
    fun rowIndexedValueIterator() {
        assertMatrixIndexedValueIteratorEquals(
                cnot().rowsIndexedValueIterator(),
                arrayOf(
                        intArrayOf(0),
                        intArrayOf(1),
                        intArrayOf(3),
                        intArrayOf(2)
                ),
                arrayOf(
                        arrayOf(Complex.One),
                        arrayOf(Complex.One),
                        arrayOf(Complex.One),
                        arrayOf(Complex.One)
                )
        )
    }

}