package quantum.gate

import org.junit.Test
import quantum.core.Complex
import quantum.core.InverseSqrt2
import quantum.core.toComplex
import quantum.junit.assertComplexEquals
import kotlin.test.assertEquals

class CNotGateTest {

    @Test
    fun testElements() {

        val cnot = cnot()

        assertEquals(4, cnot.rows)
        assertEquals(4, cnot.columns)

        assertComplexEquals(Complex.One, cnot[0, 0])
        assertComplexEquals(Complex.Zero, cnot[0, 1])
        assertComplexEquals(Complex.Zero, cnot[1, 0])
        assertComplexEquals(Complex.One, cnot[1, 1])

        assertComplexEquals(Complex.Zero, cnot[0, 2])
        assertComplexEquals(Complex.Zero, cnot[0, 3])
        assertComplexEquals(Complex.Zero, cnot[1, 2])
        assertComplexEquals(Complex.Zero, cnot[1, 3])

        assertComplexEquals(Complex.Zero, cnot[2, 0])
        assertComplexEquals(Complex.Zero, cnot[2, 1])
        assertComplexEquals(Complex.Zero, cnot[3, 0])
        assertComplexEquals(Complex.Zero, cnot[3, 1])

        assertComplexEquals(Complex.Zero, cnot[2, 2])
        assertComplexEquals(Complex.One, cnot[2, 3])
        assertComplexEquals(Complex.One, cnot[3, 2])
        assertComplexEquals(Complex.Zero, cnot[3, 3])
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testRowOutOfBounds() {
        cnot()[4, 3]
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testColumnOutOfBounds() {
        cnot()[2, 4]
    }
}