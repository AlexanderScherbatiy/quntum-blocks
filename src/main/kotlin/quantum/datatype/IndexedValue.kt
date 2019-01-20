package quantum.datatype

data class IndexedValue<V>(val index: Int, val value: V)

interface IndexedValueIterator<V> {
    val size: Int
    val zeroValue: V
    fun hasNext(): Boolean

    fun next(consumer: (index: Int, value: V) -> Unit)

    infix fun zip(other: IndexedValueIterator<V>): IndexedPairIterator<V> =
            quantum.datatype.zip(this, other)

    infix fun zipNonZero(other: IndexedValueIterator<V>): IndexedPairIterator<V> =
            quantum.datatype.zipNonZero(this, other)

}

interface IndexedPairIterator<V> {
    fun hasNext(): Boolean
    fun next(consumer: (index: Int, value1: V, value2: V) -> Unit)
}

fun <V> equals(iter1: IndexedValueIterator<V>, iter2: IndexedValueIterator<V>): Boolean {

    if (iter1.size != iter2.size) {
        return false
    }

    val iter = zip(iter1, iter2)

    var flag = true
    while (iter.hasNext()) {
        iter.next { _, value1, value2 ->
            flag = value1 == value2
        }
        if (!flag) return false
    }

    return true
}

class IndexedArrayValueIterator<V>(override val zeroValue: V,
                                   val indices: IntArray,
                                   val values: Array<V>) : IndexedValueIterator<V> {

    private var index = 0
    override val size = indices.size

    override fun hasNext() = index < indices.size

    override fun next(consumer: (Int, V) -> Unit) {
        if (index >= indices.size) {
            outOfBounds(index)
        }
        consumer(indices[index], values[index])
        index++
    }
}

class IndexedArraySkipZeroValueIterator<V>(override val zeroValue: V,
                                           val values: Array<V>) : IndexedValueIterator<V> {

    private var index = -1
    override val size = values.count { it != zeroValue }

    init {
        skipZeros()
    }

    private fun skipZeros() {
        while (++index < values.size && values[index] == zeroValue);
    }

    override fun hasNext() = index < values.size

    override fun next(consumer: (Int, V) -> Unit) {
        if (index >= values.size) {
            outOfBounds(index)
        }
        consumer(index, values[index])
        skipZeros()
    }
}

private fun outOfBounds(index: Int): Nothing =
        throw NoSuchElementException("IndexedValueIterator out of bounds: $index")

private abstract class AbstractIndexedPairIterator<V>(val iter1: IndexedValueIterator<V>,
                                                      val iter2: IndexedValueIterator<V>) : IndexedPairIterator<V> {
    var index1 = -1
    var index2 = -1

    var value1 = iter1.zeroValue
    var value2 = iter2.zeroValue

    init {
        next1()
        next2()
    }

    protected fun valid(index: Int) = index != -1

    protected fun next1() {
        if (iter1.hasNext()) {
            iter1.next { index, value ->
                index1 = index
                value1 = value
            }
        } else {
            index1 = -1
            value1 = iter1.zeroValue
        }
    }

    protected fun next2() {
        if (iter2.hasNext()) {
            iter2.next { index, value ->
                index2 = index
                value2 = value
            }
        } else {
            index2 = -1
            value2 = iter2.zeroValue
        }
    }
}

private fun <V> zip(iter1: IndexedValueIterator<V>,
                    iter2: IndexedValueIterator<V>): IndexedPairIterator<V> =
        object : AbstractIndexedPairIterator<V>(iter1, iter2) {

            override fun hasNext() = index1 != -1 || index2 != -1

            override fun next(consumer: (index: Int, value1: V, value2: V) -> Unit) = when {

                !valid(index1) && !valid(index2) -> outOfBounds(0)

                valid(index1) && !valid(index2) -> {
                    consumer(index1, value1, iter2.zeroValue)
                    next1()
                }

                !valid(index1) && valid(index2) -> {
                    consumer(index2, iter2.zeroValue, value2)
                    next2()
                }

                index1 < index2 -> {
                    consumer(index1, value1, iter2.zeroValue)
                    next1()
                }

                index1 > index2 -> {
                    consumer(index2, iter1.zeroValue, value2)
                    next2()
                }
                else -> {
                    consumer(index1, value1, value2)
                    next1()
                    next2()
                }
            }
        }

private fun <V> zipNonZero(iter1: IndexedValueIterator<V>,
                           iter2: IndexedValueIterator<V>): IndexedPairIterator<V> =
        object : AbstractIndexedPairIterator<V>(iter1, iter2) {

            init {
                moveToTheSameIndices()
            }

            override fun hasNext() = valid(index1) && valid(index2)

            private fun moveToTheSameIndices() {
                while (true)
                    if (!valid(index1) || !valid(index2)) {
                        break
                    } else if (index1 < index2) {
                        next1()
                    } else if (index1 > index2) {
                        next2()
                    } else {
                        break
                    }
            }

            override fun next(consumer: (index: Int, value1: V, value2: V) -> Unit) =
                    if (!valid(index1) || !valid(index2)) {
                        outOfBounds(0)
                    } else {
                        consumer(index1, value1, value2)
                        next1()
                        next2()
                        moveToTheSameIndices()
                    }
        }