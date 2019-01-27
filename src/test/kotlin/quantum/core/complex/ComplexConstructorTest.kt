package quantum.core.complex

import org.junit.Test
import quantum.core.Complex.Companion.complex
import quantum.util.assertComplexEquals
import kotlin.test.assertEquals

class ComplexConstructorTest {

    @Test
    fun testConstructor() {

        assertEquals(2.0, complex(2.0, 3.0).real)
        assertEquals(3.0, complex(2.0, 3.0).imaginary)

        assertComplexEquals(complex(4.0, 0.0), complex(4.0))
        assertComplexEquals(complex(4.0, 5.0), complex(4.0, 5.0))
        assertComplexEquals(complex(2.0, 0.0), complex(2.0))
    }
}

