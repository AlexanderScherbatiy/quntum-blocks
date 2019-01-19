package quantum.datatype

import org.junit.Test

class IndexedValueIteratorZipNonZeroTest {

    @Test
    fun testIteratorZipNonZero() {

        val iterable1 = IndexedStringValueIterable(intArrayOf(), arrayOf())
        val iterable2 = IndexedStringValueIterable(intArrayOf(), arrayOf())

        val iter = iterable1 zipNonZero iterable2
        checkEnd(iter)
    }

    @Test
    fun testIteratorZipNonZero_11() {

        val iterable1 = IndexedStringValueIterable(intArrayOf(3), arrayOf("31"))
        val iterable2 = IndexedStringValueIterable(intArrayOf(3), arrayOf("32"))

        val iter = iterable1 zipNonZero iterable2

        checkValues(iter, 3, "31", "32", "")
        checkEnd(iter)
    }

    @Test
    fun testIteratorZipNonZero_10() {

        val iterable1 = IndexedStringValueIterable(intArrayOf(5), arrayOf("5"))
        val iterable2 = IndexedStringValueIterable(intArrayOf(), arrayOf())

        val iter = iterable1 zipNonZero iterable2

        checkEnd(iter)
    }

    @Test
    fun testIteratorZipNonZero_01() {

        val iterable1 = IndexedStringValueIterable(intArrayOf(), arrayOf())
        val iterable2 = IndexedStringValueIterable(intArrayOf(7), arrayOf("7"))

        val iter = iterable1 zipNonZero iterable2

        checkEnd(iter)
    }

    @Test
    fun testIteratorZipNonZero_11_11() {

        val iterable1 = IndexedStringValueIterable(intArrayOf(3, 5), arrayOf("31", "51"))
        val iterable2 = IndexedStringValueIterable(intArrayOf(3, 5), arrayOf("32", "52"))

        val iter = iterable1 zipNonZero iterable2

        checkValues(iter, 3, "31", "32", "")
        checkValues(iter, 5, "51", "52", "")
        checkEnd(iter)
    }

    @Test
    fun testIteratorZipNonZero_10_01() {

        val iterable1 = IndexedStringValueIterable(intArrayOf(3), arrayOf("3"))
        val iterable2 = IndexedStringValueIterable(intArrayOf(5), arrayOf("5"))

        val iter = iterable1 zipNonZero iterable2

        checkEnd(iter)
    }

    @Test
    fun testIteratorZipNonZero_11_10() {

        val iterable1 = IndexedStringValueIterable(intArrayOf(3, 5), arrayOf("31", "5"))
        val iterable2 = IndexedStringValueIterable(intArrayOf(3), arrayOf("32"))

        val iter = iterable1 zipNonZero iterable2

        checkValues(iter, 3, "31", "32", "")
        checkEnd(iter)
    }

    @Test
    fun testIteratorZipNonZero_01_11() {

        val iterable1 = IndexedStringValueIterable(intArrayOf(5), arrayOf("51"))
        val iterable2 = IndexedStringValueIterable(intArrayOf(3, 5), arrayOf("3", "52"))

        val iter = iterable1 zipNonZero iterable2

        checkValues(iter, 5, "51", "52", "")
        checkEnd(iter)
    }
}