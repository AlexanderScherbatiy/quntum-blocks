package quantum.datatype

import org.junit.Test

class IndexedValueIteratorZipTest {

    @Test
    fun testIteratorZip() {

        val iterable1 = IndexedArrayValueIterator("",intArrayOf(), arrayOf())
        val iterable2 = IndexedArrayValueIterator("",intArrayOf(), arrayOf())

        val iter = iterable1 zip iterable2
        checkEnd(iter)
    }

    @Test
    fun testIteratorZip_11() {

        val iterable1 = IndexedArrayValueIterator("",intArrayOf(3), arrayOf("31"))
        val iterable2 = IndexedArrayValueIterator("",intArrayOf(3), arrayOf("32"))

        val iter = iterable1 zip iterable2

        checkValues(iter, 3, "31", "32", "")
        checkEnd(iter)
    }

    @Test
    fun testIteratorZip_10() {

        val iterable1 = IndexedArrayValueIterator("",intArrayOf(5), arrayOf("5"))
        val iterable2 = IndexedArrayValueIterator("",intArrayOf(), arrayOf())

        val iter = iterable1 zip iterable2

        checkValues(iter, 5, "5", "", "")
        checkEnd(iter)
    }

    @Test
    fun testIteratorZip_01() {

        val iterable1 = IndexedArrayValueIterator("",intArrayOf(), arrayOf())
        val iterable2 = IndexedArrayValueIterator("",intArrayOf(7), arrayOf("7"))

        val iter = iterable1 zip iterable2

        checkValues(iter, 7, "", "7", "")
        checkEnd(iter)
    }

    @Test
    fun testIteratorZip_11_11() {

        val iterable1 = IndexedArrayValueIterator("",intArrayOf(3, 5), arrayOf("31", "51"))
        val iterable2 = IndexedArrayValueIterator("",intArrayOf(3, 5), arrayOf("32", "52"))

        val iter = iterable1 zip iterable2

        checkValues(iter, 3, "31", "32", "")
        checkValues(iter, 5, "51", "52", "")
        checkEnd(iter)
    }

    @Test
    fun testIteratorZip_10_01() {

        val iterable1 = IndexedArrayValueIterator("",intArrayOf(3), arrayOf("3"))
        val iterable2 = IndexedArrayValueIterator("",intArrayOf(5), arrayOf("5"))

        val iter = iterable1 zip iterable2

        checkValues(iter, 3, "3", "", "")
        checkValues(iter, 5, "", "5", "")
        checkEnd(iter)
    }

    @Test
    fun testIteratorZip_11_10() {

        val iterable1 = IndexedArrayValueIterator("",intArrayOf(3, 5), arrayOf("31", "5"))
        val iterable2 = IndexedArrayValueIterator("",intArrayOf(3), arrayOf("32"))

        val iter = iterable1 zip iterable2

        checkValues(iter, 3, "31", "32", "")
        checkValues(iter, 5, "5", "", "")
        checkEnd(iter)
    }

    @Test
    fun testIteratorZip_01_11() {

        val iterable1 = IndexedArrayValueIterator("",intArrayOf(5), arrayOf("51"))
        val iterable2 = IndexedArrayValueIterator("",intArrayOf(3, 5), arrayOf("3", "52"))

        val iter = iterable1 zip iterable2

        checkValues(iter, 3, "", "3", "")
        checkValues(iter, 5, "51", "52", "")
        checkEnd(iter)
    }
}