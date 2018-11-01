package quantum.blocks

interface QuantumState {
    val size: Int
    fun get(index: Int): Complex
}


fun quantumState(coefficients: List<Complex>): QuantumState {

    val sqr = coefficients.map { it.sqr() }.sum()

    if (sqr == 1.0) {
        return QuantumStateImp(coefficients)
    }

    val r = kotlin.math.sqrt(sqr)

    return QuantumStateImp(coefficients.map { it / r })
}

private class QuantumStateImp(val coefficients: List<Complex>) : QuantumState {

    override val size = coefficients.size

    override fun get(index: Int) = coefficients.get(index)

}