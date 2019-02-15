package quantum.core

import quantum.core.Complex.Companion.Zero
import quantum.datatype.AbstractIndexedSkipZeroValueIterator
import quantum.datatype.IndexedValueIterator

interface QuantumGate {

    val size: Int

    operator fun get(row: Int, column: Int): Complex

    fun rowsIndexedValueIterator(): QuantumGateMatrixIndexedValueIterator

    fun columnsIndexedValueIterator(): QuantumGateMatrixIndexedValueIterator

    operator fun times(state: QuantumState): QuantumState {

        val rows = rowsIndexedValueIterator()
        val coefficients = Array(size) { Complex.Zero }

        for (index in (0 until size)) {
            val zipIter = (rows.iterator(index) zipNonZero state.indexedValueIterator())
            while (zipIter.hasNext()) {
                zipIter.next { _, value1, value2 ->
                    coefficients[index] += value1 * value2
                }
            }
        }

        return quantumState(*coefficients)
    }

    operator fun times(other: QuantumGate): QuantumGate {

        val rows = this.rowsIndexedValueIterator()
        val columns = other.columnsIndexedValueIterator()

        for (i in (0 until rows.size)) {
            for (j in (0 until rows.size)) {
                val zip = rows.iterator(i) zipNonZero columns.iterator(j)

                var value = Complex.Zero
                while (zip.hasNext()) {
                    zip.next { _, value1, value2 ->
                        value += value1 * value2
                    }
                }
            }
        }

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

open class MatrixQuantumGate(val elements: Array<Array<Complex>>) : AbstractConstantQuantumGate() {

    override val size = elements.size

    override fun get(row: Int, column: Int) = elements[row][column]

    override fun toString() = contentToString()
}

abstract class AbstractConstantQuantumGate : QuantumGate {

    override fun rowsIndexedValueIterator() = QuantumGateRowsIndexedValueIterator(this)

    override fun columnsIndexedValueIterator() = QuantumGateColumnsIndexedValueIterator(this)
}

object EmptyIndexedComplexValueIterator : IndexedValueIterator<Complex> {
    override val size = 0
    override val zeroValue = Zero
    override fun hasNext() = false

    override fun next(consumer: (index: Int, value: Complex) -> Unit) = throw IndexOutOfBoundsException("0")
}

abstract class QuantumGateIndexedValueIterator(val gateSize: Int)
    : IndexedValueIterator<IndexedValueIterator<Complex>> {

    override val size = gateSize

    override val zeroValue = EmptyIndexedComplexValueIterator

    private var index = 0

    override fun hasNext() = index < gateSize

    abstract fun elem(i: Int, j: Int): Complex

    override fun next(consumer: (index: Int, value: IndexedValueIterator<Complex>) -> Unit) {

        val row = object : AbstractIndexedSkipZeroValueIterator<Complex>() {
            override val zeroValue = Zero
            override val valuesSize = gateSize
            override val size = getNonZeroSize()

            override fun get(i: Int) = elem(index, i)

            init {
                initSkipZeroIterator()
            }
        }

        consumer(index, row)
        index++
    }
}

interface QuantumGateMatrixIndexedValueIterator {
    val size: Int
    fun iterator(index: Int): IndexedValueIterator<Complex>
}

abstract class QuantumGateMatrixSkipZeroIndexedValueIterator(val matrixSize: Int) : QuantumGateMatrixIndexedValueIterator {

    override val size = matrixSize

    abstract fun elem(i: Int, j: Int): Complex

    override fun iterator(index: Int) = object : AbstractIndexedSkipZeroValueIterator<Complex>() {
        override val zeroValue = Complex.Zero
        override val valuesSize = matrixSize
        override val size = getNonZeroSize()

        override fun get(i: Int) = elem(index, i)

        init {
            initSkipZeroIterator()
        }
    }
}

class QuantumGateRowsIndexedValueIterator(val gate: QuantumGate)
    : QuantumGateMatrixSkipZeroIndexedValueIterator(gate.size) {
    override fun elem(i: Int, j: Int) = gate[i, j]
}

class QuantumGateColumnsIndexedValueIterator(val gate: QuantumGate)
    : QuantumGateMatrixSkipZeroIndexedValueIterator(gate.size) {
    override fun elem(i: Int, j: Int) = gate[j, i]
}
