package quantum.junit

import org.junit.Assert
import quantum.blocks.Complex

private const val Delta = 1e-12

fun assertComplexEquals(expected: Complex, actual: Complex) {
    Assert.assertEquals(expected.real, actual.real, Delta)
    Assert.assertEquals(expected.imaginary, actual.imaginary, Delta)
}