package quantum.core.gate

import org.junit.Test
import quantum.core.Complex
import quantum.core.MatrixQuantumGate
import quantum.core.Qubit
import quantum.util.InverseSqrt2
import quantum.util.assertComplexIndexedValueIteratorEquals

class MatrixGateScalarProductTest {

    @Test
    fun testScalarProductQubit() {

        val gate = MatrixQuantumGate(
                arrayOf(
                        arrayOf(Complex.Zero, -Complex.I),
                        arrayOf(Complex.I, Complex.Zero)
                )
        )

        val i = Complex.I
        val elem = InverseSqrt2 * i

        assertComplexIndexedValueIteratorEquals(
                (gate * Qubit.Zero).indexedValueIterator(),
                intArrayOf(1),
                arrayOf(i)
        )

        assertComplexIndexedValueIteratorEquals(
                (gate * Qubit.One).indexedValueIterator(),
                intArrayOf(0),
                arrayOf(-i)
        )

        assertComplexIndexedValueIteratorEquals(
                (gate * Qubit.Plus).indexedValueIterator(),
                intArrayOf(0, 1),
                arrayOf(-elem, elem)
        )
    }
}