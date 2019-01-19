package quantum.datatype

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class IndexedValueIteratorEqualsTest {

    @Test
    fun testIteratorZipNonZero() {

        assertTrue(quantum.datatype.equals(
                IndexedArrayValueIterator("", intArrayOf(), arrayOf()),
                IndexedArrayValueIterator("", intArrayOf(), arrayOf())
        ))
    }

    @Test
    fun testIteratorZipNonZero_11() {

        assertTrue(quantum.datatype.equals(
                IndexedArrayValueIterator("", intArrayOf(3), arrayOf("30")),
                IndexedArrayValueIterator("", intArrayOf(3), arrayOf("30"))
        ))

        assertFalse(quantum.datatype.equals(
                IndexedArrayValueIterator("", intArrayOf(3), arrayOf("31")),
                IndexedArrayValueIterator("", intArrayOf(3), arrayOf("32"))
        ))
    }

    @Test
    fun testIteratorZipNonZero_10() {

        assertFalse(quantum.datatype.equals(
                IndexedArrayValueIterator("", intArrayOf(3), arrayOf("3")),
                IndexedArrayValueIterator("", intArrayOf(), arrayOf())
        ))
    }

    @Test
    fun testIteratorZipNonZero_01() {

        assertFalse(quantum.datatype.equals(
                IndexedArrayValueIterator("", intArrayOf(), arrayOf()),
                IndexedArrayValueIterator("", intArrayOf(3), arrayOf("3"))
        ))
    }

    @Test
    fun testIteratorZipNonZero_11_11() {

        assertTrue(quantum.datatype.equals(
                IndexedArrayValueIterator("", intArrayOf(3, 7), arrayOf("3", "7")),
                IndexedArrayValueIterator("", intArrayOf(3, 7), arrayOf("3", "7"))
        ))

        assertFalse(quantum.datatype.equals(
                IndexedArrayValueIterator("", intArrayOf(3, 7), arrayOf("31", "7")),
                IndexedArrayValueIterator("", intArrayOf(3, 7), arrayOf("32", "7"))
        ))

        assertFalse(quantum.datatype.equals(
                IndexedArrayValueIterator("", intArrayOf(3, 7), arrayOf("3", "71")),
                IndexedArrayValueIterator("", intArrayOf(3, 7), arrayOf("3", "72"))
        ))

        assertFalse(quantum.datatype.equals(
                IndexedArrayValueIterator("", intArrayOf(3, 7), arrayOf("31", "71")),
                IndexedArrayValueIterator("", intArrayOf(3, 7), arrayOf("32", "72"))
        ))
    }

    @Test
    fun testIteratorZipNonZero_10_01() {

        assertFalse(quantum.datatype.equals(
                IndexedArrayValueIterator("", intArrayOf(3), arrayOf("1")),
                IndexedArrayValueIterator("", intArrayOf(7), arrayOf("1"))
        ))
    }

    @Test
    fun testIteratorZipNonZero_11_10() {

        assertFalse(quantum.datatype.equals(
                IndexedArrayValueIterator("", intArrayOf(3, 7), arrayOf("1", "1")),
                IndexedArrayValueIterator("", intArrayOf(7), arrayOf("1"))
        ))
    }

    @Test
    fun testIteratorZipNonZero_01_11() {

        assertFalse(quantum.datatype.equals(
                IndexedArrayValueIterator("", intArrayOf(3), arrayOf("1")),
                IndexedArrayValueIterator("", intArrayOf(3, 7), arrayOf("1", "1"))
        ))
    }
}