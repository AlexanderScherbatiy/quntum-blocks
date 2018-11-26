package quantum.builder

import org.junit.Test
import quantum.core.Bit
import quantum.core.Qubit
import quantum.gate.controlled
import quantum.gate.hadamar
import quantum.gate.identity
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BalancedFunctionBuilderTest {

    @Test
    fun testBalancedFunction1() {
        assertFalse(isBalanced1 { Bit.Zero })
        assertTrue(isBalanced1 { it })
        assertTrue(isBalanced1 { !it })
        assertFalse(isBalanced1 { Bit.One })
    }

    @Test
    fun testBalancedFunction2() {
        assertFalse(isBalanced2 { Bit.Zero })
        assertTrue(isBalanced2 { it })
        assertTrue(isBalanced2 { !it })
        assertFalse(isBalanced2 { Bit.One })
    }

    private fun isBalanced1(f: (Bit) -> Bit): Boolean {
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

    private fun isBalanced2(f: (Bit) -> Bit): Boolean {
        val amplitudes = QuantumAlgorithm()
                .inputs(Qubit.Zero, Qubit.One)
                .gateLayers()
                .layer(hadamar() tensorProduct hadamar())
                .layer(controlled(f))
                .layer(hadamar() tensorProduct hadamar())
                .end()
                .measure(Qubit.One.tensorProduct(Qubit.One))
                .result()
        return amplitudes[0].norm() > 0.5
    }
}