package quantum.complex

import org.junit.Test
import quantum.blocks.Complex.Companion.complex
import kotlin.test.assertEquals

class ComplexConstructorTest {

    @Test
    fun testcomplexConstructor() {

        assertEquals(2.0, complex(2.0, 3.0).real)
        assertEquals(3.0, complex(2.0, 3.0).imaginary)

        assertEquals(complex(4.0, 0.0), complex(4.0))
        assertEquals(complex(4.0, 5.0), complex(4.0, 5.0))
        assertEquals(complex(2.0, 0.0), complex(2.0))
    }
}

