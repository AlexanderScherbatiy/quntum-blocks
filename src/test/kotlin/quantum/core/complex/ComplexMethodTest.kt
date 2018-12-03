package quantum.core.complex

import org.junit.Test
import quantum.core.Complex.Companion.I
import quantum.core.Complex.Companion.One
import quantum.core.Complex.Companion.Zero
import quantum.core.Complex.Companion.complex
import quantum.junit.assertComplexEquals
import kotlin.test.assertEquals

class ComplexMethodTest {

    @Test
    fun testEquals() {
        assertComplexEquals(complex(0.0, 0.0), complex(0.0, -0.0))
        assertComplexEquals(complex(0.0, 0.0), complex(-0.0, 0.0))
        assertComplexEquals(complex(0.0, 0.0), complex(-0.0, -0.0))
    }

    @Test
    fun testHashcode() {

        assertEquals(complex(0.0, 0.0).hashCode(), complex(0.0, -0.0).hashCode())
        assertEquals(complex(0.0, 0.0).hashCode(), complex(-0.0, 0.0).hashCode())
        assertEquals(complex(0.0, 0.0).hashCode(), complex(-0.0, -0.0).hashCode())
    }

    @Test
    fun testToString() {
        assertEquals("1.00", One.toString())
        assertEquals("i", I.toString())
        assertEquals("2.70", complex(2.7).toString())
        assertEquals("3.40i", complex(0.0, 3.4).toString())
        assertEquals("1.20+3.40i", complex(1.2, 3.4).toString())
        assertEquals("2.30-5.60i", complex(2.3, -5.6).toString())
    }

    @Test
    fun testConjugate() {
        assertComplexEquals(Zero, Zero.conjugate())
        assertComplexEquals(One, One.conjugate())
        assertComplexEquals(-I, I.conjugate())
        assertComplexEquals(complex(1.2, -2.3), complex(1.2, 2.3).conjugate())
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