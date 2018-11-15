package quantum.jmh

import quantum.blocks.Complex

private class ArrayQuantumState(val coefficients: Array<Complex>) : QuantumState {

    override val size = coefficients.size

    override fun get(index: Int) = coefficients[index]

    override fun toString() = "QuantumState(${coefficients.joinToString()})"
}

fun arrayQuantumState(coefficients: Array<Complex>): QuantumState = ArrayQuantumState(normalize(coefficients))

private fun normalize(elems: Array<Complex>): Array<Complex> {
    val sqr = elems.map { it.sqr() }.sum()

    if (sqr == 1.0) {
        return elems
    }

    val r = kotlin.math.sqrt(sqr)
    return Array(elems.size) { i -> elems[i] / r }
}
