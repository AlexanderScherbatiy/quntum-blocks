package quantum.gate

import org.junit.Test
import quantum.core.Complex
import quantum.core.Qubit
import quantum.core.toComplex
import quantum.datatype.checkValue
import quantum.util.assertIndexedValueIteratorEquals
import quantum.util.assertMatrixIndexedValueIteratorEquals
import quantum.util.assertStateEquals
import quantum.util.showIndexedValueIteratorEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class IdentityGateTest {

    @Test
    fun testElements() {
        val identity = identity()

        assertEquals(2, identity.size)

        assertEquals(1.0.toComplex(), identity[0, 0])
        assertEquals(0.0.toComplex(), identity[0, 1])
        assertEquals(0.0.toComplex(), identity[1, 0])
        assertEquals(1.0.toComplex(), identity[1, 1])
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testRowOutOfBounds() {
        identity()[2, 0]
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testColumnOutOfBounds() {
        identity()[0, 2]
    }

    @Test
    fun testProductState() {

        val identity = identity()
        assertStateEquals(Qubit.Zero, identity * Qubit.Zero)
        assertStateEquals(Qubit.One, identity * Qubit.One)
        assertStateEquals(Qubit.Plus, identity * Qubit.Plus)
        assertStateEquals(Qubit.Minus, identity * Qubit.Minus)
    }

    @Test
    fun rowIndexedValueIterator() {
        assertMatrixIndexedValueIteratorEquals(
                identity().rowIndexedValueIterator(),
                arrayOf(
                        intArrayOf(0),
                        intArrayOf(1)
                ),
                arrayOf(
                        arrayOf(Complex.One),
                        arrayOf(Complex.One)
                )
        )
    }
}