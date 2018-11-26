package quantum.bit

import org.junit.Test
import quantum.core.Bit
import kotlin.test.assertEquals

class BitTest {
    @Test
    fun operatorTest() {

        assertEquals(Bit.One, !Bit.Zero)

        assertEquals(Bit.Zero, Bit.Zero + Bit.Zero)
        assertEquals(Bit.One, Bit.Zero + Bit.One)
        assertEquals(Bit.One, Bit.One + Bit.Zero)
        assertEquals(Bit.Zero, Bit.One + Bit.One)

        assertEquals(Bit.Zero, Bit.Zero * Bit.Zero)
        assertEquals(Bit.Zero, Bit.Zero * Bit.One)
        assertEquals(Bit.Zero, Bit.One * Bit.Zero)
        assertEquals(Bit.One, Bit.One * Bit.One)
    }
}