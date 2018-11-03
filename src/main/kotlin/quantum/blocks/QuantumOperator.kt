package quantum.blocks

interface QuantumOperator {

    val rows: Int
    val columns: Int

    fun get(row: Int, column: Int): Double
}

fun hadamar() = HadamarQuantumOperator

object HadamarQuantumOperator : QuantumOperator {

    override val rows = 2
    override val columns = 2

    override fun get(row: Int, column: Int): Double {
        when {
            row == 0 && column == 0 -> return inverseSqrt2
            row == 0 && column == 1 -> return inverseSqrt2
            row == 1 && column == 0 -> return inverseSqrt2
            row == 1 && column == 1 -> return -inverseSqrt2
            else -> throw IndexOutOfBoundsException(
                    "indices ($row, $column), dimensions: ($rows, $columns)")
        }
    }
}