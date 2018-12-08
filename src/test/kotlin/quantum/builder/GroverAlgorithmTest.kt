package quantum.builder

import org.junit.Test
import quantum.core.*
import quantum.gate.controlled
import quantum.gate.diffusion
import quantum.gate.hadamar
import quantum.gate.identity
import kotlin.test.assertEquals

class GroverAlgorithmTest {

    @Test
    fun testGroverAlgorithm() {

        val inputBits = "100110".toBits()
        val n = inputBits.size
        val times = Math.round(Math.sqrt(n.toDouble())).toInt()
        val f = { x: List<Bit> -> (x == inputBits).toBit() }

        val Uw = controlled(n + 1, f)

        val hadamarN = tensorProduct(n, hadamar())
        val groverDiffusion = hadamarN * diffusion(tensorProduct(n, Qubit.Zero)) * hadamarN
        val Us = groverDiffusion tensorProduct identity()


        val outputState = QuantumAlgorithm()
//                .logLevel("finer")
                .input(*Array(n) { Qubit.Zero }, Qubit.One)
                .layer(hadamarN tensorProduct hadamar())
                .repeat(times)
                .layer(Uw)
                .layer(Us)
                .repeatEnd()
                .run()
                .outputState

        val measuredTimes = 100
        val map = mutableMapOf<Int, Int>()
        for (i in 1..measuredTimes) {
            val index = outputState.measureBasisIndex()
            map[index] = map.getOrElse(index) { 0 } + 1
        }

        val measuredIndex = map.maxBy { it.value }!!.key
        val measuredBits = measuredIndex.toBits().dropLast(1)
        assertEquals(inputBits, measuredBits)
    }
}