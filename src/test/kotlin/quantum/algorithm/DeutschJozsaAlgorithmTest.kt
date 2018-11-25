package quantum.algorithm

import org.junit.Test
import quantum.blocks.Bit
import quantum.blocks.Qubit
import quantum.blocks.controlled
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DeutschJozsaAlgorithmTest {

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

        assertFalse(isBalanced { _ -> Bit.Zero })
        assertFalse(isBalanced { _ -> Bit.One })
        assertTrue(isBalanced { bit -> bit })
        assertTrue(isBalanced { bit -> bit.inverse() })
    }

    private fun isBalanced(f: (bit: Bit) -> Bit): Boolean {

        val x = Qubit.Plus
        val y = Qubit.Minus
        val input = x.tensorProduct(y)
        val u = controlled(f)
        val output = u * input
        val projection = Qubit.Minus.tensorProduct(Qubit.Minus)
        val probability = (projection * output).norm()
        return probability > 0.5
    }
}