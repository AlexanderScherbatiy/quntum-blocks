package quantum.jmh

import org.openjdk.jmh.annotations.*
import quantum.blocks.Complex
import java.util.concurrent.TimeUnit
import org.openjdk.jmh.annotations.Param

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
open class ListQuantumStateBenchmark {


    @Param("0", "1", "2", "3", "4", "5")
    var value = 0

    var initialCoefficients: List<Complex> = listOf()

    @Setup
    fun setUp() {
        initialCoefficients = BenchmarkUtil.getRandomComplexValues()
    }

    @Benchmark
    fun listQuantumStateBenchmark(): QuantumState {
        return listQuantumState(initialCoefficients)
    }
}