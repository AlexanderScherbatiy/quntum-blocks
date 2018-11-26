package quantum.core.bit

import org.junit.Test
import quantum.core.Bit
import quantum.core.toBit
import quantum.core.toBits
import kotlin.test.assertEquals

class BitTest {
    @Test
    fun testOperators() {

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


    @Test
    fun testToBit() {

        assertEquals(Bit.Zero, false.toBit())
        assertEquals(Bit.One, true.toBit())
    }

    @Test
    fun testToBits() {

        assertEquals(listOf(Bit.Zero), 0.toBits())
        assertEquals(listOf(Bit.One), 1.toBits())
        assertEquals(listOf(Bit.One, Bit.Zero), 2.toBits())
        assertEquals(listOf(Bit.One, Bit.One), 3.toBits())
        assertEquals(listOf(Bit.One, Bit.Zero, Bit.Zero), 4.toBits())

        assertEquals(listOf(Bit.One, Bit.Zero, Bit.One), "101".toBits())
    }

}