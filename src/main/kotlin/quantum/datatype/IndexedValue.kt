package quantum.datatype

data class IndexedValue<V>(val index: Int, val value: V)

interface IndexedValueIterator<V> {
    val size: Int
    val zeroValue: V
    fun hasNext(): Boolean

    fun next(consumer: (index: Int, value: V) -> Unit)
}

interface IndexedPairIterator<V> {
    fun hasNext(): Boolean
    fun next(consumer: (index: Int, value1: V, value2: V) -> Unit)
}

interface IndexedValueIterable<V> {
    fun iterator(): IndexedValueIterator<V>
    infix fun zip(other: IndexedValueIterable<V>): IndexedPairIterator<V> = quantum.datatype.zip(this, other)
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

private fun outOfBounds(index: Int): Nothing =
        throw NoSuchElementException("IndexedValueIterator out of bounds: $index")

private fun <V> zip(iterable1: IndexedValueIterable<V>, iterable2: IndexedValueIterable<V>): IndexedPairIterator<V> =
        object : IndexedPairIterator<V> {
            val iter1 = iterable1.iterator()
            val iter2 = iterable2.iterator()

            var index1 = -1
            var index2 = -1

            var value1 = iter1.zeroValue
            var value2 = iter2.zeroValue

            init {
                next1()
                next2()
            }

            override fun hasNext() = index1 != -1 || index2 != -1

            private fun valid(index: Int) = index != -1

            private fun next1() {
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

            private fun next2() {
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
