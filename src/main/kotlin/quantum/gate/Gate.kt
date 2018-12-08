package quantum.gate

import quantum.core.*


fun identity(size: Int) = object : QuantumGate {

    override val size = size

    override fun get(row: Int, column: Int): Complex {
        checkDimensions(row, column)
        return if (row == column) Complex.One else Complex.Zero
    }
}

fun identity() = IdentityQuantumGate
fun hadamar() = HadamarQuantumGate
fun cnot() = CNotGate
fun projection(state: QuantumState) = projection(state, state)
fun projection(state1: QuantumState, state2: QuantumState) = ProjectionGate(state1, state2)
fun diffusion(state: QuantumState) = GroverDiffusionGate(state)

object IdentityQuantumGate : QuantumGate {

    override val size = 2

    override fun get(row: Int, column: Int) = when {
        row == 0 && column == 0 -> Complex.One
        row == 0 && column == 1 -> Complex.Zero
        row == 1 && column == 0 -> Complex.Zero
        row == 1 && column == 1 -> Complex.One
        else -> throwDimensionException(row, column)
    }
}

object HadamarQuantumGate : QuantumGate {

    override val size = 2

    override fun get(row: Int, column: Int) = when {
        row == 0 && column == 0 -> InverseSqrt2
        row == 0 && column == 1 -> InverseSqrt2
        row == 1 && column == 0 -> InverseSqrt2
        row == 1 && column == 1 -> -InverseSqrt2
        else -> throwDimensionException(row, column)
    }
}

object CNotGate : QuantumGate {

    override val size = 4

    override fun get(row: Int, column: Int) = when {
        row == 0 && column == 0 -> Complex.One
        row == 1 && column == 1 -> Complex.One
        row == 2 && column == 3 -> Complex.One
        row == 3 && column == 2 -> Complex.One
        row < this.size && column < this.size -> Complex.Zero
        else -> throwDimensionException(row, column)
    }

    override fun toString() = contentToString()
}

data class ProjectionGate(val state1: QuantumState, val state2: QuantumState) : MatrixQuantumGate(
        Array(state1.size) { i ->
            Array(state2.size) { j ->
                state1[i] scalar state2[j]
            }
        })

data class GroverDiffusionGate(val state: QuantumState) : MatrixQuantumGate(
        Array(state.size) { i ->
            Array(state.size) { j ->
                fun diracDeltaFun() = if (i == j) 1.0 else 0.0
                2.0 * (state[i] scalar state[j]) - diracDeltaFun()
            }
        })

fun controlled(f: (Bit) -> Bit): QuantumGate = controlled(2) { f(it.first()) }

fun controlled(size: Int, f: (List<Bit>) -> Bit): QuantumGate {

    val opSize = 1 shl size

    val cache: Array<Bit> = (0 until (opSize shr 1))
            .map { f(it.toBits()) }
            .toTypedArray()

    return object : QuantumGate {
        override val size get() = opSize

        override fun get(row: Int, column: Int): Complex {

            if (row >= this.size || column >= this.size)
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

        override fun toString() = contentToString()
    }
}
