package quantum.core

import quantum.core.Complex.Companion.Zero

interface QuantumGate {

    val rows: Int
    val columns: Int

    operator fun get(row: Int, column: Int): Complex

    operator fun times(other: QuantumState): QuantumState {

        val coefficients = Array(rows) { i ->
            (0 until columns)
                    .map { j -> this[i, j] * other[j] }
                    .reduce { c1, c2 -> c1 + c2 }

        }

        return quantumState(*coefficients)
    }

    operator fun times(other: QuantumGate): QuantumGate {

        val coefficients = Array(this.rows) { i ->
            Array(other.columns) { j ->
                (0 until this.columns)
                        .map { k -> this[i, k] * other[k, j] }
                        .reduce { c1, c2 -> c1 + c2 }
            }
        }

        return MatrixQuantumGate(coefficients)
    }

    infix fun tensorProduct(other: QuantumGate): QuantumGate {

        val coefficients = Array(rows * other.rows) {
            Array(columns * other.columns) { Zero }
        }

        var baseRow = 0
        var baseColumn = 0

        for (i1 in 0 until this.rows) {
            for (j1 in 0 until this.columns) {
                for (i2 in 0 until other.rows) {
                    for (j2 in 0 until other.columns) {
                        coefficients[baseRow + i2][baseColumn + j2] = this[i1, j1] * other[i2, j2]
                    }
                }
                baseRow += other.rows
            }
            baseRow = 0
            baseColumn += other.columns
        }

        return MatrixQuantumGate(coefficients)
    }
}

fun tensorProduct(n: Int, gate: QuantumGate): QuantumGate = tensorProduct(*Array(n) { gate })

fun tensorProduct(vararg gates: QuantumGate): QuantumGate = gates.reduce { gate1, gate2 ->
    gate1 tensorProduct gate2
}

fun QuantumGate.contentToString() = buildString {
    append("QuantumGate[${rows}x${columns}]{")
    for (i in 0 until rows) {
        append("{")
        for (j in 0 until columns) {
            append(get(i, j))
            append(" ")
        }
        append("}")
    }
    append("}")
    toString()
}


fun QuantumGate.checkDimensions(row: Int, column: Int) {
    if (row < rows && column < columns) {
        return
    }
    throwDimensionException(row, column)
}

fun QuantumGate.throwDimensionException(row: Int, column: Int): Nothing =
        throw IndexOutOfBoundsException(
                "indices ($row, $column), dimensions: ($rows, $columns)")

open class MatrixQuantumGate(val elems: Array<Array<Complex>>) : QuantumGate {

    override val rows = elems.size
    override val columns = if (elems.isEmpty()) 0 else elems[0].size

    override fun get(row: Int, column: Int) = elems[row][column]

    override fun toString() = contentToString()
}