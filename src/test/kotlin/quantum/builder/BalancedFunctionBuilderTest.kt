package quantum.builder

import org.junit.Test
import quantum.core.Bit
import quantum.core.Qubit
import quantum.core.toBits
import quantum.gate.controlled
import quantum.gate.hadamar
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
        val outputState = QuantumAlgorithm()
                .inputs(Qubit.Plus, Qubit.Minus)
                .gatesLayer(controlled(f))
                .run()
                .outputState

        val basis = Qubit.Minus tensorProduct Qubit.Minus
        return (outputState * basis).norm() > 0.5
    }

    private fun isBalanced2(f: (Bit) -> Bit): Boolean {
        return QuantumAlgorithm()
                .inputs(Qubit.Zero, Qubit.One)
                .gatesLayer(hadamar() tensorProduct hadamar())
                .gatesLayer(controlled(f))
                .gatesLayer(hadamar() tensorProduct hadamar())
                .run()
                .measureStandardBasisBits() == "11".toBits()
    }
}