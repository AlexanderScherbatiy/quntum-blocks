package quantum.core.gate

import org.junit.Test
import quantum.core.*
import quantum.util.InverseSqrt2
import quantum.util.assertComplexIndexedValueIteratorEquals
import quantum.util.assertStateEquals

class MatrixGateProductStateTest {

    @Test
    fun testProductQubit() {

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

    @Test
    fun testProductState() {

        val angle = 28 * Math.PI / 180
        val cos = Math.cos(angle).toComplex()
        val sin = Math.sin(angle).toComplex()

        val gate = MatrixQuantumGate(arrayOf(
                arrayOf(cos, -sin),
                arrayOf(sin, cos)
        ))

        assertStateEquals(qubit(cos, sin), gate * Qubit.Zero)
        assertStateEquals(qubit(-sin, cos), gate * Qubit.One)
        assertStateEquals(qubit(cos - sin, sin + cos), gate * Qubit.Plus)
        assertStateEquals(qubit(cos + sin, sin - cos), gate * Qubit.Minus)
    }

}