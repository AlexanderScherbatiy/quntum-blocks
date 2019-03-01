package quantum.core

import kotlin.random.Random

/**
 * Return index of the basis which has been measured
 */
fun QuantumState.measureBasisIndex(): Int {

    val iter = this.indexedValueIterator()
    val N = iter.size
    val array = DoubleArray(N)
    val indices = IntArray(N)

    var i = 0
    while (iter.hasNext()) {
        iter.next { index, value ->
            indices[i] = index
            array[i] = value.sqr()
            i++
        }
    }

    for (i in (1 until N)) {
        array[i] += array[i - 1]
    }

    val probability = Random.nextDouble(1.0)

    for (i in 0 until N) {
        if (probability < array[i]) {
            return indices[i]
        }
    }

    return indices[N - 1]
}

data class Measurement(val bits: Bits, val state: QuantumState)

fun QuantumState.measureBits(vararg indices: Int): Measurement {

    val bitsSize = kotlin.math.log2(size.toDouble()).toInt()

    val indexValuePairs = mutableListOf<Pair<Int, Complex>>()
    val iter = indexedValueIterator()
    while (iter.hasNext()) {
        iter.next { index, value ->
            indexValuePairs.add(Pair(index, value))
        }
    }

    val measuredBitsMap = indexValuePairs
            .groupBy { it.first.toBitsArray(bitsSize).slice(*indices) }

    val measuredBitsAmplitudesArray = measuredBitsMap
            .mapValues { it.value.map { it.second.sqr() }.sum() }
            .entries.toTypedArray()

    val probabilities = measuredBitsAmplitudesArray.map { it.value }.toDoubleArray()
    val measuredIndex = measureIndex(*probabilities)

    val measuredBits = measuredBitsAmplitudesArray[measuredIndex].key
    val measuredStateMap = measuredBitsMap
            .getOrDefault(measuredBits, listOf())
            .filter { it.second != Complex.Zero }
            .toMap()

    return Measurement(measuredBits, quantumState(size, measuredStateMap))
}


private fun measureIndex(vararg probabilities: Double): Int {

    val size = probabilities.size
    val array = probabilities.toTypedArray()

    for (i in (1 until size)) {
        array[i] += array[i - 1]
    }

    val probability = Random.nextDouble(1.0)

    for (i in 0 until size) {
        if (probability < array[i]) {
            return i
        }
    }

    return size - 1
}
