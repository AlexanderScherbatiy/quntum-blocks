package quantum.util

import org.junit.Assert
import quantum.core.Complex
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
                                         values: Array<V>) {

    assertEquals(iter.size, indices.size)

    for (i in (0 until indices.size)) {
        assertTrue(iter.hasNext())
        iter.next { index, value ->
            assertEquals(indices[i], index)
            assertEquals(values[i], value)
        }
    }
    assertFalse(iter.hasNext())
}

fun <V> assertMatrixIndexedValueIteratorEquals(iter: IndexedValueIterator<IndexedValueIterator<V>>,
                                               indices: Array<IntArray>,
                                               values: Array<Array<V>>) {

    assertEquals(iter.size, indices.size)

    for (i in (0 until indices.size)) {
        assertTrue(iter.hasNext())
        iter.next { index, iterValue ->
            assertEquals(i, index)
            assertIndexedValueIteratorEquals(iterValue, indices[i], values[i])
        }
    }
    assertFalse(iter.hasNext())
}

fun <V> showIndexedValueIteratorEquals(iter: IndexedValueIterator<V>) {
    println("size: ${iter.size}")
    while (iter.hasNext()) {
        iter.next { index, value ->
            println("index: $index, value: $value")
        }
    }
}