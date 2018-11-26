package quantum.builder

import org.junit.Test
import quantum.core.Bit
import quantum.core.Qubit
import quantum.gate.controlled
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BalancedFunctionBuilderTest {

    @Test
    fun test() {
        assertFalse(isBalanced { Bit.Zero })
        assertTrue(isBalanced { it })
        assertTrue(isBalanced { !it })
        assertFalse(isBalanced { Bit.One })
    }

    private fun isBalanced(f: (Bit) -> Bit): Boolean {
        val amplitudes = QuantumAlgorithm()
                .inputs(Qubit.Plus, Qubit.Minus)
                .gates(controlled(f))
                .measure(Qubit.Plus.tensorProduct(Qubit.Plus),
                        Qubit.Plus.tensorProduct(Qubit.Minus),
                        Qubit.Minus.tensorProduct(Qubit.Plus),
                        Qubit.Minus.tensorProduct(Qubit.Minus))
                .result()
        return amplitudes[3].norm() > 0.5
    }
}