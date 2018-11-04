package quantum.complex

import org.junit.Test
import quantum.blocks.Complex.Companion.complex
import kotlin.test.assertEquals

class ComplexOperationTest {

    @Test
    fun testDivNumber() {
        val div = complex(1.0, 2.0) / 2.0
        assertEquals(complex(0.5, 1.0), div)
    }

    @Test
    fun testPluscomplex() {
        val sum = complex(1.0, 2.0) + complex(3.0, 4.0)
        assertEquals(complex(4.0, 6.0), sum)
    }

    @Test
    fun testMulcomplex() {
        assertEquals(complex(6.0), complex(2.0) * complex(3.0))
        assertEquals(complex(-6.0), complex(0.0, 2.0) * complex(0.0, 3.0))
        assertEquals(complex(-5.0, 10.0), complex(1.0, 2.0) * complex(3.0, 4.0))
    }
}