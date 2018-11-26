package quantum.gate

import quantum.core.*


fun identity() = IdentityQuantumGate
fun hadamar() = HadamarQuantumGate


object IdentityQuantumGate : QuantumGate {

    override val rows = 2
    override val columns = 2

    override fun get(row: Int, column: Int) = when {
        row == 0 && column == 0 -> Complex.One
        row == 0 && column == 1 -> Complex.Zero
        row == 1 && column == 0 -> Complex.Zero
        row == 1 && column == 1 -> Complex.One
        else -> throwDimensionException(row, column)
    }
}

object HadamarQuantumGate : QuantumGate {

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

fun controlled(f: (Bit) -> Bit): QuantumGate = controlled(2) { f(it.first()) }

fun controlled(size: Int, f: (List<Bit>) -> Bit): QuantumGate {

    val opSize = 1 shl size

    val cache: Array<Bit> = (0 until (opSize shr 1))
            .map { f(it.toBits()) }
            .toTypedArray()

    return object : QuantumGate {
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
                +1 -> if (isEven(column)) cache[div2(row)].toComplex() else Complex.Zero
                -1 -> if (isEven(row)) cache[div2(column)].toComplex() else Complex.Zero
                else -> Complex.Zero
            }
        }

        override fun toString() = contentToString(this)
    }
}
