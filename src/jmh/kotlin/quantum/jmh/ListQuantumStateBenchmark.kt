package quantum.jmh

import org.openjdk.jmh.annotations.*
import quantum.blocks.Complex

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 10)
@Measurement(iterations = 1)
open class ListQuantumStateBenchmark {
    var initialCoefficients: List<Complex>? = null

    @Setup
    fun setUp() {
        initialCoefficients = listOf(Complex.One, Complex.One)
    }

    @Benchmark
    fun listQuantumStateBenchmark() {
        val coefficients = initialCoefficients!!
        val quantumState = listQuantumState(coefficients)
    }
}