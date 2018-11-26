package quantum.complex

import org.junit.Test
import quantum.core.plus
import quantum.core.times
import quantum.core.minus
import quantum.core.toComplex
import quantum.core.Complex.Companion.I
import quantum.core.Complex.Companion.complex
import kotlin.test.assertEquals

class ComplexArithmeticTest {

    val i = I

    @Test
    fun testSum() {

        assertEquals(complex(1.0, 1.0), 1.0 + i)
        assertEquals(complex(1.0, 1.0), i + 1.0 )
    }

    @Test
    fun testMinus() {

        assertEquals(complex(3.0, -1.0), 3.0 - i)
        assertEquals(complex(-3.0, 1.0), i - 3.0)
    }

    @Test
    fun testTimes() {

        assertEquals(complex(0.0, 3.0), 3.0 * i)
        assertEquals(complex(0.0, 3.0), i * 3.0)
    }

    @Test
    fun testExpression() {

        assertEquals(complex(6.0, 8.0), 2.0 * (3.0 + 4.0 * i))
        assertEquals(complex(6.0, 8.0), (3.0 + 4.0 * i) * 2.0)
    }

    @Test
    fun testTocomplex() {

        assertEquals(complex(1.0, 0.0), 1.0.toComplex())
        assertEquals(complex(2.5, 0.0), 2.5.toComplex())
        assertEquals(complex(-3.5, 0.0), (-3.5).toComplex())
    }
}