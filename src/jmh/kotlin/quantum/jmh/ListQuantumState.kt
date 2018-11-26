package quantum.jmh

import quantum.core.Complex

private class ListQuantumState(val coefficients: List<Complex>) : QuantumState {

    override val size = coefficients.size

    override fun get(index: Int) = coefficients[index]

    override fun toString() = "QuantumState(${coefficients.joinToString()})"
}

fun listQuantumState(coefficients: List<Complex>): QuantumState = ListQuantumState(normalize(coefficients))

private fun normalize(elems: List<Complex>): List<Complex> {
    val sqr = elems.map { it.sqr() }.sum()

    if (sqr == 1.0) {
        return elems
    }

    val r = kotlin.math.sqrt(sqr)

    return elems.map { it / r }
}
