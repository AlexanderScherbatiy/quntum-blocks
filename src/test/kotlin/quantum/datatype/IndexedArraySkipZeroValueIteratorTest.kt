package quantum.datatype

import org.junit.Test
import kotlin.test.assertEquals

class IndexedArraySkipZeroValueIteratorTest {

    @Test
    fun testSkipZeroIterator0() {

        val iter = IndexedArraySkipZeroValueIterator("", arrayOf())

        assertEquals(0, iter.size)
        checkEnd(iter)
    }

    @Test
    fun testIterator1() {

        val iter = IndexedArraySkipZeroValueIterator("", arrayOf("3"))

        assertEquals(1, iter.size)
        checkValue(iter, 0, "3", "")
        checkEnd(iter)
    }

    @Test
    fun testIterator01() {

        val iter = IndexedArraySkipZeroValueIterator("", arrayOf("", "5"))

        assertEquals(1, iter.size)
        checkValue(iter, 1, "5", "")
        checkEnd(iter)
    }

    @Test
    fun testIterator10() {

        val iter = IndexedArraySkipZeroValueIterator("", arrayOf("3", ""))

        assertEquals(1, iter.size)
        checkValue(iter, 0, "3", "")
        checkEnd(iter)
    }

    @Test
    fun testIterator11() {

        val iter = IndexedArraySkipZeroValueIterator("", arrayOf("3", "5"))

        assertEquals(2, iter.size)
        checkValue(iter, 0, "3", "")
        checkValue(iter, 1, "5", "")
        checkEnd(iter)
    }

    @Test
    fun testIterator100() {

        val iter = IndexedArraySkipZeroValueIterator("", arrayOf("2", "", ""))

        assertEquals(1, iter.size)
        checkValue(iter, 0, "2", "")
        checkEnd(iter)
    }

    @Test
    fun testIterator010() {

        val iter = IndexedArraySkipZeroValueIterator("", arrayOf("", "3", ""))

        assertEquals(1, iter.size)
        checkValue(iter, 1, "3", "")
        checkEnd(iter)
    }

    @Test
    fun testIterator001() {

        val iter = IndexedArraySkipZeroValueIterator("", arrayOf("", "", "5"))

        assertEquals(1, iter.size)
        checkValue(iter, 2, "5", "")
        checkEnd(iter)
    }

    @Test
    fun testIterator101() {

        val iter = IndexedArraySkipZeroValueIterator("", arrayOf("3", "", "5"))

        assertEquals(2, iter.size)
        checkValue(iter, 0, "3", "")
        checkValue(iter, 2, "5", "")
        checkEnd(iter)
    }
}