package quantum.core.gate

import org.junit.Test
import quantum.core.Complex
import quantum.core.MatrixQuantumGate
import quantum.util.assertMatrixIndexedValueIteratorEquals

class MatrixGateProductGateTest {


    /**
     * ( 0 -i ) * (0 1) = ( -i  0)
     * ( i  0 )   (1 0)   (  0  i)
     */
    @Test
    fun testProductGate2x2() {

        val gate1 = MatrixQuantumGate(
                arrayOf(
                        arrayOf(Complex.Zero, -Complex.I),
                        arrayOf(Complex.I, Complex.Zero)
                )
        )

        val gate2 = MatrixQuantumGate(
                arrayOf(
                        arrayOf(Complex.Zero, Complex.One),
                        arrayOf(Complex.One, Complex.Zero)
                )
        )

        assertMatrixIndexedValueIteratorEquals(
                (gate1 * gate2).rowIndexedValueIterator(),
                arrayOf(
                        intArrayOf(0),
                        intArrayOf(1)
                ),
                arrayOf(
                        arrayOf(-Complex.I),
                        arrayOf(Complex.I)
                )
        )
    }

    /**
     * ( 1  0  0  0 ) * ( 0 -i  0  0 ) = ( 0 -i  0  0 )
     * ( 0  1  0  0 ) * ( i  0  0  0 ) = ( i  0  0  0 )
     * ( 0  0  0  1 ) * ( 0  0  1  0 ) = ( 0  0  0 -1 )
     * ( 0  0  1  0 ) * ( 0  0  0 -1 ) = ( 0  0  1  0 )
     */
    @Test
    fun testProductGate4x4() {

        val gate1 = MatrixQuantumGate(
                arrayOf(
                        arrayOf(Complex.One, Complex.Zero, Complex.Zero, Complex.Zero),
                        arrayOf(Complex.Zero, Complex.One, Complex.Zero, Complex.Zero),
                        arrayOf(Complex.Zero, Complex.Zero, Complex.Zero, Complex.One),
                        arrayOf(Complex.Zero, Complex.Zero, Complex.One, Complex.Zero)
                )
        )

        val gate2 = MatrixQuantumGate(
                arrayOf(
                        arrayOf(Complex.Zero, -Complex.I, Complex.Zero, Complex.Zero),
                        arrayOf(Complex.I, Complex.Zero, Complex.Zero, Complex.Zero),
                        arrayOf(Complex.Zero, Complex.Zero, Complex.One, Complex.Zero),
                        arrayOf(Complex.Zero, Complex.Zero, Complex.Zero, -Complex.One)
                )
        )

        assertMatrixIndexedValueIteratorEquals(
                (gate1 * gate2).rowIndexedValueIterator(),
                arrayOf(
                        intArrayOf(1),
                        intArrayOf(0),
                        intArrayOf(3),
                        intArrayOf(2)
                ),
                arrayOf(
                        arrayOf(-Complex.I),
                        arrayOf(Complex.I),
                        arrayOf(-Complex.One),
                        arrayOf(Complex.One)
                )
        )
    }
}