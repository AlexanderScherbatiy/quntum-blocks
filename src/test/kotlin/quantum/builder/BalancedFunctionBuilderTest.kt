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
        // input: Qubit.Zero x Qubit.One
        // |01>
        // layer: Hadamar x Hadamar
        // |01> -> |+-> =
        // (|0>+|1>)(|0>-|1>) / 2 =
        // (|00>-|01>+|10>-|11>) / 2
        // layer: controlled(f)
        // f(x) = 0
        // layer: Hadamar x Hadamar
        // |01>
        assertFalse(isBalanced2 { Bit.Zero })
        assertTrue(isBalanced2 { it })
        assertTrue(isBalanced2 { !it })
        assertFalse(isBalanced2 { Bit.One })
    }

    private fun isBalanced1(f: (Bit) -> Bit): Boolean {
        val outputState = QuantumAlgorithm()
                .input(Qubit.Plus, Qubit.Minus)
                .layer(controlled(f))
                .run()
                .outputState

        val basis = Qubit.Minus tensorProduct Qubit.Minus
        return (outputState * basis).norm() > 0.5
    }

    // Hadamar x Hadamar
    // 1/2 *
    //( 1  1) x ( 1  1)
    //( 1 -1) x ( 1 -1)
    // = 1 / 2
    // ( 1  1  1  1)
    // ( 1 -1  1 -1)
    // ( 1  1 -1 -1)
    // ( 1 -1 -1  1)
    //
    // controlled(f) =
    // ( !f0  f0   0    0 )
    // (  f0 !f0   0    0 )
    // (   0   0 !f1   f1 )
    // (   0   0  f1  !f1 )
    private fun isBalanced2(f: (Bit) -> Bit): Boolean {
        return QuantumAlgorithm()
                .logLevel("info")
                .input(Qubit.Zero, Qubit.One)
                .layer(hadamar() tensorProduct hadamar())
                .layer(controlled(f))
                .layer(hadamar() tensorProduct hadamar())
                .run()
                .measureStandardBasisBits() == "11".toBits()
    }
}