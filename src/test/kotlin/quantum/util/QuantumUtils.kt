package quantum.util

import org.junit.Assert
import quantum.core.Complex
import quantum.core.QuantumGateMatrixIndexedValueIterator
import quantum.core.QuantumState
import quantum.core.normalize
import quantum.datatype.IndexedValueIterator
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

private const val Delta = 1e-12

fun testHashCode(vararg objects: Any): Int = Objects.hash(*objects)
fun testStateHash(vararg objects: Complex): Int = testHashCode(*normalize(*objects))

fun assertHashEquals(state: QuantumState, vararg coefficients: Complex) {
    assertEquals(testStateHash(*coefficients), state.hashCode())
}

fun assertHashNotEquals(state: QuantumState, vararg coefficients: Complex) {
    assertNotEquals(testStateHash(*coefficients), state.hashCode())
}

fun assertStateEquals(expected: QuantumState, actual: QuantumState) {

    val iter1 = expected.indexedValueIterator()
    val iter2 = actual.indexedValueIterator()
    val iter = iter1 zip iter2

    while (iter.hasNext()) {
        iter.next { _, value1, value2 ->
            assertComplexEquals(value1, value2)
        }
    }
}

fun assertComplexEquals(expected: Complex, actual: Complex) {
    Assert.assertEquals(expected.real, actual.real, Delta)
    Assert.assertEquals(expected.imaginary, actual.imaginary, Delta)
}

fun <V> assertIndexedValueIteratorEquals(iter: IndexedValueIterator<V>,
                                         indices: IntArray,
                                         values: Array<V>,
                                         equals: (v1: V, v2: V) -> Boolean) {

    assertEquals(iter.size, indices.size)

    for (i in (0 until indices.size)) {
        assertTrue(iter.hasNext())
        iter.next { index, value ->
            assertEquals(indices[i], index)
            assertTrue(equals(values[i], value))
        }
    }
    assertFalse(iter.hasNext())
}

fun assertComplexIndexedValueIteratorEquals(iter: IndexedValueIterator<Complex>,
                                            indices: IntArray,
                                            values: Array<Complex>) {
    assertIndexedValueIteratorEquals(iter, indices, values) { c1, c2 ->
        assertComplexEquals(c1, c2)
        true
    }
}

fun assertMatrixIndexedValueIteratorEquals(iter: QuantumGateMatrixIndexedValueIterator,
                                           indices: Array<IntArray>,
                                           values: Array<Array<Complex>>) {

    assertEquals(iter.size, indices.size)

    for (i in (0 until iter.size)) {
        assertIndexedValueIteratorEquals(iter.iterator(i), indices[i], values[i]) { v1, v2 ->
            assertComplexEquals(v1, v2)
            true
        }
    }
}

fun <V> showIndexedValueIterator(iter: IndexedValueIterator<V>) {
    println("size: ${iter.size}")
    while (iter.hasNext()) {
        iter.next { index, value ->
            println("index: $index, value: $value")
        }
    }
}