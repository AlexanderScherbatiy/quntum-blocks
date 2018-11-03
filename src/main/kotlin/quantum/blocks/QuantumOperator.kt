package quantum.blocks

interface QuantumOperator {

    val rows: Int
    val columns: Int

    fun get(row: Int, column: Int): Double
}

fun identity() = IdentityQuantumOperator
fun hadamar() = HadamarQuantumOperator

object IdentityQuantumOperator : QuantumOperator {

    override val rows = 2
    override val columns = 2

    override fun get(row: Int, column: Int) = when {
        row == 0 && column == 0 -> 1.0
        row == 0 && column == 1 -> 0.0
        row == 1 && column == 0 -> 0.0
        row == 1 && column == 1 -> 1.0
        else -> throwDimensionException(row, column)
    }
}

object HadamarQuantumOperator : QuantumOperator {

    override val rows = 2
    override val columns = 2

    override fun get(row: Int, column: Int) = when {
        row == 0 && column == 0 -> inverseSqrt2
        row == 0 && column == 1 -> inverseSqrt2
        row == 1 && column == 0 -> inverseSqrt2
        row == 1 && column == 1 -> -inverseSqrt2
        else -> throwDimensionException(row, column)
    }
}

private fun QuantumOperator.throwDimensionException(row: Int, column: Int): Nothing =
        throw IndexOutOfBoundsException(
                "indices ($row, $column), dimensions: ($rows, $columns)")