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

    infix fun tensorProduct(other: QuantumGate): QuantumGate {

        val coefficients = Array(rows * other.rows) {
            Array(columns * other.columns) { Zero }
        }

        var baseRow = 0
        var baseColumn = 0

        for (i1 in 0 until rows) {
            for (j1 in 0 until columns) {
                for (i2 in 0 until rows) {
                    for (j2 in 0 until columns) {
                        coefficients[baseRow + i2][baseColumn + j2] = this[i1, j1] * other[i2, j2]
                    }
                }
                baseRow += other.rows
            }
            baseRow = 0
            baseColumn += other.columns
        }

        return ArrayQuantumGate(coefficients)
    }
}

fun contentToString(op: QuantumGate) = buildString {
    append("QuantumGate{")
    for (i in 0 until op.rows) {
        append("{")
        for (j in 0 until op.columns) {
            append(op[i, j])
            append(" ")
        }
        append("}")
    }
    append("}")
    toString()
}


fun QuantumGate.throwDimensionException(row: Int, column: Int): Nothing =
        throw IndexOutOfBoundsException(
                "indices ($row, $column), dimensions: ($rows, $columns)")

private data class ArrayQuantumGate(val elems: Array<Array<Complex>>) : QuantumGate {

    override val rows = elems.size
    override val columns = if (elems.isEmpty()) 0 else elems[0].size

    override fun get(row: Int, column: Int) = elems[row][column]
}