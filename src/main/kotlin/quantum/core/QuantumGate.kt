package quantum.core

import quantum.core.Complex.Companion.Zero
import quantum.datatype.AbstractIndexedSkipZeroValueIterator
import quantum.datatype.IndexedValueIterator

interface QuantumGate {

    val size: Int

    operator fun get(row: Int, column: Int): Complex

    fun rowIndexedValueIterator(): IndexedValueIterator<IndexedValueIterator<Complex>>

    operator fun times(other: QuantumState): QuantumState {

        val iter = other.indexedValueIterator()
        val coefficients = Array(size) { Complex.Zero }

        while (iter.hasNext()) {
            iter.next { column, value ->
                for (row in 0 until size) {
                    coefficients[row] += this[row, column] * value
                }
            }
        }

        return quantumState(*coefficients)
    }

    operator fun times(other: QuantumGate): QuantumGate {

        val coefficients = Array(this.size) { i ->
            Array(other.size) { j ->
                (0 until this.size)
                        .map { k -> this[i, k] * other[k, j] }
                        .reduce { c1, c2 -> c1 + c2 }
            }
        }

        return MatrixQuantumGate(coefficients)
    }

    infix fun tensor(other: QuantumGate): QuantumGate {

        val newSize = this.size * other.size
        val coefficients = Array(newSize) {
            Array(newSize) { Zero }
        }

        var baseRow = 0
        var baseColumn = 0

        for (i1 in 0 until this.size) {
            for (j1 in 0 until this.size) {
                for (i2 in 0 until other.size) {
                    for (j2 in 0 until other.size) {
                        coefficients[baseRow + i2][baseColumn + j2] = this[i1, j1] * other[i2, j2]
                    }
                }
                baseRow += other.size
            }
            baseRow = 0
            baseColumn += other.size
        }

        return MatrixQuantumGate(coefficients)
    }
}

fun tensor(n: Int, gate: QuantumGate): QuantumGate = tensor(*Array(n) { gate })

fun tensor(vararg gates: QuantumGate): QuantumGate = gates.reduce { gate1, gate2 ->
    gate1 tensor gate2
}

fun QuantumGate.contentToString() = buildString {
    append("QuantumGate size: $size {")
    for (i in 0 until size) {
        append("{")
        for (j in 0 until size) {
            append(get(i, j))
            append(" ")
        }
        append("}")
    }
    append("}")
    toString()
}


fun QuantumGate.checkDimensions(row: Int, column: Int) {
    if (row < this.size && column < this.size) {
        return
    }
    throwDimensionException(row, column)
}

fun QuantumGate.throwDimensionException(row: Int, column: Int): Nothing =
        throw IndexOutOfBoundsException(
                "indices ($row, $column), dimensions: (${this.size}, ${this.size})")

open class MatrixQuantumGate(val elements: Array<Array<Complex>>) : QuantumGate {

    override val size = elements.size

    override fun get(row: Int, column: Int) = elements[row][column]

    override fun rowIndexedValueIterator() = QuantumGateRowIndexedValueIterator(this)

    override fun toString() = contentToString()
}

object EmptyIndexedComplexValueIterator : IndexedValueIterator<Complex> {
    override val size = 0
    override val zeroValue = Zero
    override fun hasNext() = false

    override fun next(consumer: (index: Int, value: Complex) -> Unit) = throw IndexOutOfBoundsException("0")
}

class QuantumGateRowIndexedValueIterator(val gate: QuantumGate)
    : IndexedValueIterator<IndexedValueIterator<Complex>> {

    override val size = gate.size

    override val zeroValue = EmptyIndexedComplexValueIterator

    private var index = 0

    override fun hasNext() = index < gate.size

    override fun next(consumer: (index: Int, value: IndexedValueIterator<Complex>) -> Unit) {

        val row = object : AbstractIndexedSkipZeroValueIterator<Complex>() {
            override val zeroValue = Zero
            override val valuesSize = gate.size
            override val size = getNonZeroSize()

            override fun get(i: Int) = gate[index, i]

            init {
                initSkipZeroIterator()
            }
        }

        consumer(index, row)
        index++
    }
}