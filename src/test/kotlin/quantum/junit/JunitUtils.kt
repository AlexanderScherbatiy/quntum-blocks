package quantum.junit

import org.junit.Assert
import quantum.core.Complex
import quantum.core.QuantumState
import quantum.core.normalize
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private const val Delta = 1e-12

fun testHashCode(vararg objects: Any): Int = Objects.hash(*objects)
fun testQuantumStateHash(vararg objects: Complex): Int = testHashCode(*normalize(*objects))

fun assertHashEquals(state: QuantumState, vararg coefficients: Complex) {
    assertEquals(testQuantumStateHash(*coefficients), state.hashCode())
}

fun assertHashNotEquals(state: QuantumState, vararg coefficients: Complex) {
    assertNotEquals(testQuantumStateHash(*coefficients), state.hashCode())
}

fun assertComplexEquals(expected: Complex, actual: Complex) {
    Assert.assertEquals(expected.real, actual.real, Delta)
    Assert.assertEquals(expected.imaginary, actual.imaginary, Delta)
}