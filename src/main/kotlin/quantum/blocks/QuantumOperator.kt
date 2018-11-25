package quantum.blocks

import quantum.blocks.Complex.Companion.One
import quantum.blocks.Complex.Companion.Zero

interface QuantumOperator {

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

    fun tensorProduct(other: QuantumOperator): QuantumOperator {

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

        return ArrayQuantumOperator(coefficients)
    }
}

fun identity() = IdentityQuantumOperator
fun hadamar() = HadamarQuantumOperator

fun controlled(f: (Bit) -> Bit): QuantumOperator = controlled(2) { f(it.first()) }

fun controlled(size: Int, f: (List<Bit>) -> Bit): QuantumOperator {

    val opSize = 1 shl size

    val cache: Array<Bit> = (0 until (opSize shr 1))
            .map { f(it.toBits()) }
            .toTypedArray()

    return object : QuantumOperator {
        override val rows get() = opSize
        override val columns get() = opSize

        override fun get(row: Int, column: Int): Complex {

            if (row >= rows || column >= columns)
                throwDimensionException(row, column)

            fun Bit.toComplex() = when (this) {
                Bit.Zero -> Complex.Zero
                Bit.One -> Complex.One
            }

            val delta = row - column

            fun isEven(i: Int) = (i and 0x1) == 0
            fun div2(i: Int) = i shr 1

            return when (delta) {
                0 -> cache[row shr 1].inverse().toComplex()
                +1 -> if (isEven(column)) cache[div2(row)].toComplex() else Zero
                -1 -> if (isEven(row)) cache[div2(column)].toComplex() else Zero
                else -> Zero
            }
        }

        override fun toString() = contentToString(this)
    }
}

object IdentityQuantumOperator : QuantumOperator {

    override val rows = 2
    override val columns = 2

    override fun get(row: Int, column: Int) = when {
        row == 0 && column == 0 -> One
        row == 0 && column == 1 -> Zero
        row == 1 && column == 0 -> Zero
        row == 1 && column == 1 -> One
        else -> throwDimensionException(row, column)
    }
}

object HadamarQuantumOperator : QuantumOperator {

    override val rows = 2
    override val columns = 2

    override fun get(row: Int, column: Int) = when {
        row == 0 && column == 0 -> InverseSqrt2
        row == 0 && column == 1 -> InverseSqrt2
        row == 1 && column == 0 -> InverseSqrt2
        row == 1 && column == 1 -> -InverseSqrt2
        else -> throwDimensionException(row, column)
    }
}

private fun contentToString(op: QuantumOperator) = with(StringBuilder()) {
    append("QuantumOperator{")
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


private fun QuantumOperator.throwDimensionException(row: Int, column: Int): Nothing =
        throw IndexOutOfBoundsException(
                "indices ($row, $column), dimensions: ($rows, $columns)")

private data class ArrayQuantumOperator(val elems: Array<Array<Complex>>) : QuantumOperator {

    override val rows = elems.size
    override val columns = if (elems.isEmpty()) 0 else elems[0].size

    override fun get(row: Int, column: Int) = elems[row][column]
}