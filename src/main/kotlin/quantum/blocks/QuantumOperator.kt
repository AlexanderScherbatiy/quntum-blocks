package quantum.blocks

interface QuantumOperator {

    val rows: Int
    val columns: Int

    fun get(row: Int, column: Int): Complex

    fun tensorProduct(other: QuantumOperator): QuantumOperator {

        val elems = Array(rows * other.rows) {
            Array(columns * other.columns) { ComplexZero }
        }

        var baseRow = 0
        var baseColumn = 0

        for (i1 in 0 until rows) {
            for (j1 in 0 until columns) {
                for (i2 in 0 until rows) {
                    for (j2 in 0 until columns) {
                        elems[baseRow + i2][baseColumn + j2] = get(i1, j1) * other.get(i2, j2)
                    }
                }
                baseRow += other.rows
            }
            baseRow = 0
            baseColumn += other.columns
        }

        return ArrayQuantumOperator(elems)
    }
}

fun identity() = IdentityQuantumOperator
fun hadamar() = HadamarQuantumOperator

object IdentityQuantumOperator : QuantumOperator {

    override val rows = 2
    override val columns = 2

    override fun get(row: Int, column: Int) = when {
        row == 0 && column == 0 -> ComplexOne
        row == 0 && column == 1 -> ComplexZero
        row == 1 && column == 0 -> ComplexZero
        row == 1 && column == 1 -> ComplexOne
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

private fun QuantumOperator.throwDimensionException(row: Int, column: Int): Nothing =
        throw IndexOutOfBoundsException(
                "indices ($row, $column), dimensions: ($rows, $columns)")

private data class ArrayQuantumOperator(val elems: Array<Array<Complex>>) : QuantumOperator {

    override val rows = elems.size
    override val columns = if (elems.size == 0) 0 else elems[0].size

    override fun get(row: Int, column: Int) = elems[row][column]

}