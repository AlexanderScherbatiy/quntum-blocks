package quantum.datatype

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail

class IndexedValueIteratorZipTest {


    class IndexedStringValueIterable(val indices: IntArray, val values: Array<String>) : IndexedValueIterable<String> {
        override fun iterator(): IndexedValueIterator<String> = IndexedArrayValueIterator("", indices, values)
    }

    @Test
    fun testIteratorZip() {

        val iterable1 = IndexedStringValueIterable(intArrayOf(), arrayOf())
        val iterable2 = IndexedStringValueIterable(intArrayOf(), arrayOf())

        val iter = iterable1 zip iterable2
        checkEnd(iter)
    }

    @Test
    fun testIteratorZip_11() {

        val iterable1 = IndexedStringValueIterable(intArrayOf(3), arrayOf("31"))
        val iterable2 = IndexedStringValueIterable(intArrayOf(3), arrayOf("32"))

        val iter = iterable1 zip iterable2

        checkValues(iter, 3, "31", "32", "")
        checkEnd(iter)
    }

    @Test
    fun testIteratorZip_10() {

        val iterable1 = IndexedStringValueIterable(intArrayOf(5), arrayOf("5"))
        val iterable2 = IndexedStringValueIterable(intArrayOf(), arrayOf())

        val iter = iterable1 zip iterable2

        checkValues(iter, 5, "5", "", "")
        checkEnd(iter)
    }

    @Test
    fun testIteratorZip_01() {

        val iterable1 = IndexedStringValueIterable(intArrayOf(), arrayOf())
        val iterable2 = IndexedStringValueIterable(intArrayOf(7), arrayOf("7"))

        val iter = iterable1 zip iterable2

        checkValues(iter, 7, "", "7", "")
        checkEnd(iter)
    }

    @Test
    fun testIteratorZip_11_11() {

        val iterable1 = IndexedStringValueIterable(intArrayOf(3, 5), arrayOf("31", "51"))
        val iterable2 = IndexedStringValueIterable(intArrayOf(3, 5), arrayOf("32", "52"))

        val iter = iterable1 zip iterable2

        checkValues(iter, 3, "31", "32", "")
        checkValues(iter, 5, "51", "52", "")
        checkEnd(iter)
    }

    @Test
    fun testIteratorZip_10_01() {

        val iterable1 = IndexedStringValueIterable(intArrayOf(3), arrayOf("3"))
        val iterable2 = IndexedStringValueIterable(intArrayOf(5), arrayOf("5"))

        val iter = iterable1 zip iterable2

        checkValues(iter, 3, "3", "", "")
        checkValues(iter, 5, "", "5", "")
        checkEnd(iter)
    }

    @Test
    fun testIteratorZip_11_10() {

        val iterable1 = IndexedStringValueIterable(intArrayOf(3, 5), arrayOf("3", "51"))
        val iterable2 = IndexedStringValueIterable(intArrayOf(5), arrayOf("52"))

        val iter = iterable1 zip iterable2

        checkValues(iter, 3, "3", "", "")
        checkValues(iter, 5, "51", "52", "")
        checkEnd(iter)
    }

    @Test
    fun testIteratorZip_01_11() {

        val iterable1 = IndexedStringValueIterable(intArrayOf(5), arrayOf("51"))
        val iterable2 = IndexedStringValueIterable(intArrayOf(3, 5), arrayOf("3", "52"))

        val iter = iterable1 zip iterable2

        checkValues(iter, 3, "", "3", "")
        checkValues(iter, 5, "51", "52", "")
        checkEnd(iter)
    }


    private fun <V> checkValues(iter: IndexedPairIterator<V>, index: Int, value1: V, value2: V, zero: V) {

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

    private fun <V> checkEnd(iter: IndexedPairIterator<V>) {

        assertFalse(iter.hasNext())
        try {
            iter.next { _, _, _ -> fail("Iterator next() is called") }
            fail("Exception is not thrown")
        } catch (e: NoSuchElementException) {
        }
    }
}