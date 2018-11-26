package quantum.jmh

import quantum.core.Complex.Companion.complex
import kotlin.random.Random

object BenchmarkUtil {

    private val values = arrayListOf(
            complex(1.0, 0.0),
            complex(0.0, 1.0),
            complex(1.5, -2.3),
            complex(38.4, 16.2),
            complex(-17.3, 22.8),
            complex(-57.4, -41.9)
    )

    fun getRandomComplexValues() = (1..(values.size))
            .map { values[Random.nextInt(values.size)] }
}