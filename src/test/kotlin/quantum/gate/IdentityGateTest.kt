package quantum.gate

import org.junit.Test
import quantum.core.Qubit
import quantum.core.toComplex
import quantum.util.assertStateEquals
import kotlin.test.assertEquals

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

}