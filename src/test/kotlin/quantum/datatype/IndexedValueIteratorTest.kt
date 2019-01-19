package quantum.datatype

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail

class IndexedValueIteratorTest {

    @Test
    fun testIterator0() {

        val iter = IndexedArrayValueIterator("", intArrayOf(), arrayOf())
        assertEquals(0, iter.size)
        checkEnd(iter)
    }

    @Test
    fun testIterator1() {

        val iter = IndexedArrayValueIterator("", intArrayOf(2), arrayOf("2"))

        assertEquals(1, iter.size)
        checkValue(iter, 2, "2", "")
        checkEnd(iter)
    }

    @Test
    fun testIterator2() {

        val iter = IndexedArrayValueIterator("", intArrayOf(2, 5), arrayOf("2", "5"))

        assertEquals(2, iter.size)
        checkValue(iter, 2, "2", "")
        checkValue(iter, 5, "5", "")
        checkEnd(iter)
    }

    private fun <V> checkValue(iter: IndexedValueIterator<V>, index: Int, value: V, zero: V) {

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

    private fun <V> checkEnd(iter: IndexedValueIterator<V>) {

        assertFalse(iter.hasNext())
        try {
            iter.next { _, _ -> fail("Iterator next() is called") }
            fail("Exception is not thrown")
        } catch (e: NoSuchElementException) {
        }
    }
}