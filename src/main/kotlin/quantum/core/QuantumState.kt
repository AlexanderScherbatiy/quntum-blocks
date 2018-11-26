package quantum.core

import quantum.core.Complex.Companion.One

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

        val coefficients = Array(size * other.size) { Complex.Zero }
        var base = 0

        for (i in 0 until size) {
            for (j in 0 until other.size) {
                coefficients[base + j] = this[i] * other[j]
            }
            base += other.size
        }

        return quantumState(*coefficients)
    }
}

fun quantumState(vararg coefficients: Complex): QuantumState = QuantumStateImp(normalize(*coefficients))

fun tensorProduct(states: Array<out QuantumState>): QuantumState {

    val bounds = states.map { it.size }.toIntArray()
    val counter = IntArray(bounds.size)
    counter[0] = -1
    val coefficients = arrayListOf<Complex>()
    var hasNext = increment(counter, bounds)

    while (hasNext) {

        var c = One
        for ((stateIndex, coefficientIndex) in counter.withIndex()) {
            c *= states[stateIndex][coefficientIndex]
        }

        coefficients.add(c)
        hasNext = increment(counter, bounds)
    }

    return quantumState(*coefficients.toTypedArray())
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

private fun normalize(vararg coefficients: Complex): Array<out Complex> {
    val sqr = coefficients.map { it.sqr() }.sum()

    if (sqr == 1.0) {
        return coefficients
    }

    val r = kotlin.math.sqrt(sqr)
    return Array(coefficients.size) { coefficients[it] / r }
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
            val coefficients = normalize(zero, one)
            return Qubit(coefficients[0], coefficients[1])
        }
    }
}

private class QuantumStateImp(val coefficients: Array<out Complex>) : QuantumState {

    override val size = coefficients.size

    override fun get(index: Int) = coefficients[index]

    override fun toString() = "QuantumState(${coefficients.joinToString()})"
}
