package quantum.algorithm

import org.junit.Test
import quantum.core.Bit
import quantum.core.Qubit
import quantum.gate.controlled
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Deutsch-Jozsa Algorithm test
 */
class BalancedFunctionAlgorithmTest {

    /**
     * Unbalanced:
     * f(x) = 0
     *   0 -> 0
     *   1 -> 0
     *
     * f(x) = 1
     *   0 -> 1
     *   1 -> 1
     *
     * Balanced:
     * f(x) = x
     *   0 -> 0
     *   1 -> 1
     *
     * f(x) = not x
     *   0 -> 1
     *   1 -> 0
     *
     */
    @Test
    fun testFunctionBalanced() {

        assertFalse(isBalanced { Bit.Zero })
        assertTrue(isBalanced { it })
        assertTrue(isBalanced { !it })
        assertFalse(isBalanced { Bit.One })
    }

    private fun isBalanced(f: (bit: Bit) -> Bit): Boolean {

        val x = Qubit.Plus
        val y = Qubit.Minus
        val input = x tensor y
        val u = controlled(f)
        val output = u * input
        val projection = Qubit.Minus tensor Qubit.Minus
        val probability = (projection scalar output).norm()
        return probability > 0.5
    }
}