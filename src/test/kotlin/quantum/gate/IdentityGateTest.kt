package quantum.gate

import org.junit.Test
import quantum.core.toComplex
import kotlin.test.assertEquals

class IdentityGateTest {

    @Test
    fun testElements() {
        val identity = identity()

        assertEquals(2, identity.rows)
        assertEquals(2, identity.columns)
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
}