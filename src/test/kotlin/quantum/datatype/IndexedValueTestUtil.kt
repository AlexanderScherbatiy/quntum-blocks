package quantum.datatype

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail

class IndexedStringValueIterable(val indices: IntArray,
                                 val values: Array<String>) : IndexedValueIterable<String> {
    override fun iterator(): IndexedValueIterator<String>
            = IndexedArrayValueIterator("", indices, values)
}

fun <V> checkValue(iter: IndexedValueIterator<V>, index: Int, value: V, zero: V) {

    assertTrue(iter.hasNext())

    var i = -1
    var v = zero

    iter.next { j, u ->
        i = j
        v = u
    }

    assertEquals(index, i)
    assertEquals(value, v)
}

fun <V> checkEnd(iter: IndexedValueIterator<V>) {

    assertFalse(iter.hasNext())
    try {
        iter.next { _, _ -> fail("Iterator next() is called") }
        fail("Exception is not thrown")
    } catch (e: NoSuchElementException) {
    }
}

fun <V> checkValues(iter: IndexedPairIterator<V>, index: Int, value1: V, value2: V, zero: V) {

    assertTrue(iter.hasNext())

    var i = -1
    var v1 = zero
    var v2 = zero

    iter.next { j, u1, u2 ->
        i = j
        v1 = u1
        v2 = u2
    }

    assertEquals(index, i)
    assertEquals(value1, v1)
    assertEquals(value2, v2)
}

fun <V> checkEnd(iter: IndexedPairIterator<V>) {

    assertFalse(iter.hasNext())
    try {
        iter.next { _, _, _ -> fail("Iterator next() is called") }
        fail("Exception is not thrown")
    } catch (e: NoSuchElementException) {
    }
}
