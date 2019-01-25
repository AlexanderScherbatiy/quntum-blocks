package quantum.core

import quantum.core.Complex.Companion.Zero
import quantum.datatype.IndexedArraySkipZeroValueIterator
import quantum.datatype.IndexedArrayValueIterator
import quantum.datatype.IndexedValueIterator
import quantum.datatype.equals

interface QuantumState {
    val size: Int
    operator fun get(index: Int): Complex

    fun indexedValueIterator(): IndexedValueIterator<Complex>

    infix fun scalar(other: QuantumState): Complex {
        var scalar = Complex.Zero
        for (i in 0 until size) {
            scalar += this[i].conjugate() * other[i]
        }
        return scalar
    }

    infix fun tensor(other: QuantumState): QuantumState {

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

fun qubit(zero: Complex, one: Complex): Qubit = Qubit.from(zero, one)

fun quantumState(vararg coefficients: Complex): QuantumState = ArrayQuantumState(normalize(*coefficients))

fun quantumState(size: Int, coefficients: Map<Int, Complex>): QuantumState =
        quantumState(size, coefficients
                .toList()
                .sortedBy { it.first }
                .map { IndexedValue(it.first, it.second) })

fun quantumState(size: Int, indices: IntArray, coefficients: Array<Complex>): QuantumState =
        IndexedValueQuantumState(size, indices, normalize(*coefficients))

fun quantumState(size: Int, indexedValues: List<IndexedValue<Complex>>): QuantumState {
    val indices = indexedValues.map { it.index }.toIntArray()
    val coefficients = indexedValues.map { it.value }.toTypedArray()
    return quantumState(size, indices, coefficients)
}

fun tensor(n: Int, state: QuantumState): QuantumState =
        Array(n) { state }.reduce { s1, s2 -> s1 tensor s2 }


fun QuantumState.toArray(): Array<out Complex> = Array(size) { this[it] }

fun normalize(vararg coefficients: Complex): Array<out Complex> {
    val sqr = coefficients.map { it.sqr() }.sum()

    assert(sqr != 0.0)

    if (sqr == 1.0) {
        return coefficients
    }

    val r = kotlin.math.sqrt(sqr)
    return Array(coefficients.size) { coefficients[it] / r }
}

private fun QuantumState.stateEquals(other: Any?): Boolean = (this === other) ||
        ((other is QuantumState) && equals(this.indexedValueIterator(), other.indexedValueIterator()))

private fun QuantumState.stateHashCode(): Int = quantum.datatype.hashCode(indexedValueIterator())

data class Qubit private constructor(val zero: Complex, val one: Complex) : QuantumState {

    override val size = 2

    override fun get(index: Int) = when (index) {
        0 -> zero
        1 -> one
        else -> throw IndexOutOfBoundsException("index $index, size: 2")
    }

    override fun indexedValueIterator() =
            if (zero == Complex.Zero)
                IndexedArrayValueIterator(Complex.Zero, intArrayOf(1), arrayOf(one))
            else if (one == Complex.Zero)
                IndexedArrayValueIterator(Complex.Zero, intArrayOf(0), arrayOf(zero))
            else
                IndexedArrayValueIterator(Complex.Zero, intArrayOf(0, 1), arrayOf(zero, one))

    override fun equals(other: Any?) = stateEquals(other)

    override fun hashCode() = stateHashCode()

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

private class ArrayQuantumState(val coefficients: Array<out Complex>) : QuantumState {

    override val size = coefficients.size

    override fun get(index: Int) = coefficients[index]

    override fun toString() = "QuantumState(${coefficients.joinToString()})"

    override fun indexedValueIterator(): IndexedValueIterator<Complex> =
            IndexedArraySkipZeroValueIterator(Complex.Zero, coefficients)

    override fun equals(other: Any?) = stateEquals(other)

    override fun hashCode() = stateHashCode()
}

private class IndexedValueQuantumState(size: Int,
                                       val indices: IntArray,
                                       val coefficients: Array<out Complex>) : QuantumState {

    override val size = size

    override fun get(index: Int): Complex {
        val i = indices.indexOf(index)
        return if (i == -1) Zero else coefficients[i]
    }

    override fun toString() = "QuantumState($size, ${indices.joinToString()}, ${coefficients.joinToString()})"

    override fun indexedValueIterator(): IndexedValueIterator<Complex> =
            IndexedArrayValueIterator(Zero, indices, coefficients)

    override fun equals(other: Any?) = stateEquals(other)

    override fun hashCode() = stateHashCode()
}