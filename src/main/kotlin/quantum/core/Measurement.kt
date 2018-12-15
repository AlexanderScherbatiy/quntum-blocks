package quantum.core

import kotlin.random.Random

/**
 * Return index of the basis which has been measured
 */
fun QuantumState.measureBasisIndex(): Int {

    val array = Array(size) { this[it].sqr() }

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

data class Measurement(val bits: Bits, val state: QuantumState)

fun QuantumState.measureBits(vararg indices: Int): Measurement {

    val bitsSize = kotlin.math.log2(size.toDouble()).toInt()

    val measuredBitsMap = (0 until size)
            .map { it to this[it] }
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
