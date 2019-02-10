package quantum.core.gate

import org.junit.Test
import quantum.core.Complex
import quantum.core.MatrixQuantumGate
import quantum.util.assertComplexEquals
import quantum.util.assertMatrixIndexedValueIteratorEquals
import kotlin.test.assertEquals

class MatrixQuantumGateTest {

    @Test
    fun testGateElements() {

        val gate = MatrixQuantumGate(
                arrayOf(
                        arrayOf(Complex.Zero, -Complex.I),
                        arrayOf(Complex.I, Complex.Zero)
                )
        )

        assertEquals(2, gate.size)

        assertComplexEquals(Complex.Zero, gate[0, 0])
        assertComplexEquals(-Complex.I, gate[0, 1])
        assertComplexEquals(Complex.I, gate[1, 0])
        assertComplexEquals(Complex.Zero, gate[1, 1])
    }

    @Test
    fun testIndexedValueIterator() {

        assertMatrixIndexedValueIteratorEquals(
                MatrixQuantumGate(
                        arrayOf(
                                arrayOf(Complex.One, Complex.Zero),
                                arrayOf(Complex.Zero, -Complex.One)
                        )
                ).rowIndexedValueIterator(),
                arrayOf(
                        intArrayOf(0),
                        intArrayOf(1)
                ),
                arrayOf(
                        arrayOf(Complex.One),
                        arrayOf(-Complex.One)
                )
        )

        assertMatrixIndexedValueIteratorEquals(
                MatrixQuantumGate(
                        arrayOf(
                                arrayOf(Complex.Zero, -Complex.I),
                                arrayOf(Complex.I, Complex.Zero)
                        )
                ).rowIndexedValueIterator(),
                arrayOf(
                        intArrayOf(1),
                        intArrayOf(0)
                ),
                arrayOf(
                        arrayOf(-Complex.I),
                        arrayOf(Complex.I)
                )
        )
    }
}