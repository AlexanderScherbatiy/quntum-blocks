package quantum.datatype

import org.junit.Test
import kotlin.test.assertEquals

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
}