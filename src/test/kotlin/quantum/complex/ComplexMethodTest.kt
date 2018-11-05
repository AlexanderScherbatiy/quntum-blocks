package quantum.complex

import org.junit.Test
import quantum.blocks.Complex.Companion.One
import quantum.blocks.Complex.Companion.Zero
import quantum.blocks.Complex.Companion.complex
import kotlin.test.assertEquals

class ComplexMethodTest {

    @Test
    fun testEquals() {
        assertEquals(complex(0.0, 0.0), complex(0.0, -0.0))
        assertEquals(complex(0.0, 0.0), complex(-0.0, 0.0))
        assertEquals(complex(0.0, 0.0), complex(-0.0, -0.0))
    }

    @Test
    fun testHashcode() {

        assertEquals(complex(0.0, 0.0).hashCode(), complex(0.0, -0.0).hashCode())
        assertEquals(complex(0.0, 0.0).hashCode(), complex(-0.0, 0.0).hashCode())
        assertEquals(complex(0.0, 0.0).hashCode(), complex(-0.0, -0.0).hashCode())
    }

    @Test
    fun testToString() {
        assertEquals("2.7", complex(2.7).toString())
        assertEquals("1.2+3.4i", complex(1.2, 3.4).toString())
        assertEquals("2.3-5.6i", complex(2.3, -5.6).toString())
    }

    @Test
    fun testSqr() {
        assertEquals(0.0, Zero.sqr())
        assertEquals(1.0, One.sqr())
        assertEquals(5.0, complex(1.0, 2.0).sqr())
    }

    @Test
    fun testNorm() {
        assertEquals(0.0, Zero.sqr())
        assertEquals(1.0, One.sqr())
        assertEquals(5.0, complex(3.0, 4.0).norm())
    }
}