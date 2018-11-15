package quantum.jmh

import org.openjdk.jmh.annotations.*
import quantum.blocks.Complex

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 10)
@Measurement(iterations = 1)
open class ArrayQuantumStateBenchmark {
    var initialCoefficients: Array<Complex>? = null

    @Setup
    fun setUp() {
        initialCoefficients = arrayOf(Complex.One, Complex.One)
    }

    @Benchmark
    fun listQuantumStateBenchmark() {
        arrayQuantumState(initialCoefficients!!)
    }
}