package quantum.blocks

import quantum.blocks.Complex.Companion.One
import quantum.blocks.Complex.Companion.Zero

interface QuantumState {
    val size: Int
    operator fun get(index: Int): Complex

    operator fun times(other: QuantumState): Complex {
        var scalar = Complex.Zero
        for (i in 0 until size) {
            scalar += this[i].conjugate() * other[i]
        }
        return scalar
    }

    fun tensorProduct(other: QuantumState): QuantumState {

        val elems = Array(size * other.size) { Complex.Zero }
        var base = 0

        for (i in 0 until size) {
            for (j in 0 until other.size) {
                elems[base + j] = get(i) * other.get(j)
            }
            base += other.size
        }

        return quantumState(elems.toList())
    }
}

fun quantumState(coefficients: List<Complex>): QuantumState = QuantumStateImp(normalize(coefficients))

fun tensorProduct(states: List<QuantumState>): QuantumState {

    val bounds = states.map { it.size }.toIntArray()
    val counter = IntArray(bounds.size)
    counter[0] = -1

    val coefficients = arrayListOf<Complex>()

    var hasNext = increment(counter, bounds)

    while (hasNext) {
        val indices = counter

        var c = One

        for ((stateIndex, coefficientIndex) in indices.withIndex()) {
            c *= states.get(stateIndex).get(coefficientIndex)
        }
        coefficients.add(c)

        hasNext = increment(counter, bounds)
    }

    return quantumState(coefficients)
}


private fun increment(counter: IntArray, bounds: IntArray): Boolean {

    for (i in 0 until bounds.size) {
        counter[i]++
        if (counter[i] == bounds[i]) {
            counter[i] = 0
        } else {
            return true
        }
    }

    return false
}

private fun normalize(elems: List<Complex>): List<Complex> {
    val sqr = elems.map { it.sqr() }.sum()

    if (sqr == 1.0) {
        return elems
    }

    val r = kotlin.math.sqrt(sqr)

    return elems.map { it / r }
}

data class Qubit private constructor(val zero: Complex, val one: Complex) : QuantumState {

    override val size = 2

    override fun get(index: Int) = when (index) {
        0 -> zero
        1 -> one
        else -> throw IndexOutOfBoundsException("index $index, size: 2")
    }

    companion object {

        val Zero = Qubit(Complex.One, Complex.Zero)
        val One = Qubit(Complex.Zero, Complex.One)
        val Plus = from(Complex.One, Complex.One)
        val Minus = from(Complex.One, -Complex.One)

        fun from(zero: Complex, one: Complex): Qubit {
            val coefficients = normalize(arrayListOf(zero, one))
            return Qubit(coefficients.get(0), coefficients.get(1))
        }
    }
}

private class QuantumStateImp(val coefficients: List<Complex>) : QuantumState {

    override val size = coefficients.size

    override fun get(index: Int) = coefficients.get(index)

    override fun toString() = "QuantumState(${coefficients.joinToString()})"

}
