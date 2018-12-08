package quantum.core

import quantum.core.Complex.Companion.Zero

interface QuantumGate {

    val size: Int

    operator fun get(row: Int, column: Int): Complex

    operator fun times(other: QuantumState): QuantumState {

        val coefficients = Array(this.size) { i ->
            (0 until this.size)
                    .map { j -> this[i, j] * other[j] }
                    .reduce { c1, c2 -> c1 + c2 }

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

    override fun toString() = contentToString()
}