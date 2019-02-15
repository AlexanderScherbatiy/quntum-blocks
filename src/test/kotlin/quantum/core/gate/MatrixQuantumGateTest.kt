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
    fun testRowIndexedValueIterator() {

        assertMatrixIndexedValueIteratorEquals(
                MatrixQuantumGate(
                        arrayOf(
                                arrayOf(Complex.One, Complex.Zero),
                                arrayOf(Complex.Zero, -Complex.One)
                        )
                ).rowsIndexedValueIterator(),
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
                ).rowsIndexedValueIterator(),
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

    @Test
    fun testColumnIndexedValueIterator() {

        assertMatrixIndexedValueIteratorEquals(
                MatrixQuantumGate(
                        arrayOf(
                                arrayOf(Complex.One, Complex.Zero),
                                arrayOf(Complex.Zero, -Complex.One)
                        )
                ).columnsIndexedValueIterator(),
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
                ).columnsIndexedValueIterator(),
                arrayOf(
                        intArrayOf(1),
                        intArrayOf(0)
                ),
                arrayOf(
                        arrayOf(Complex.I),
                        arrayOf(-Complex.I)
                )
        )
    }
}