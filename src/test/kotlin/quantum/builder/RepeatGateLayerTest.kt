package quantum.builder

import org.junit.Test
import quantum.core.Qubit
import quantum.core.toBits
import quantum.gate.CNotGate
import kotlin.test.assertEquals

class RepeatGateLayerTest {

    @Test
    fun test1() {

        /**
         * input: |10> : (0, 0, 1, 0)
         * gates: CNOT
         * output: |11> : (0, 0, 0, 1)
         */

        // repeat layer (1): CNOT
        assertEquals("11".toBits(),
                QuantumAlgorithm()
                        .input(Qubit.One, Qubit.Zero)
                        .repeat(1)
                        .layer(CNotGate)
                        .repeatEnd()
                        .run()
                        .measureStandardBasisBits()
        )

        // layer           : CNOT
        // repeat layer (1): CNOT
        assertEquals("10".toBits(), QuantumAlgorithm()
                .input(Qubit.One, Qubit.Zero)
                .layer(CNotGate)
                .repeat(1)
                .layer(CNotGate)
                .repeatEnd()
                .run()
                .measureStandardBasisBits()
        )

        // repeat layer (2): CNOT
        assertEquals("10".toBits(), QuantumAlgorithm()
                .input(Qubit.One, Qubit.Zero)
                .repeat(2)
                .layer(CNotGate)
                .repeatEnd()
                .run()
                .measureStandardBasisBits()
        )

        // layer           : CNOT
        // repeat layer (2): CNOT
        assertEquals("11".toBits(), QuantumAlgorithm()
                .input(Qubit.One, Qubit.Zero)
                .layer(CNotGate)
                .repeat(2)
                .layer(CNotGate)
                .repeatEnd()
                .run()
                .measureStandardBasisBits()
        )

        // layer           : CNOT
        // repeat layer (2): CNOT
        // layer           : CNOT
        assertEquals("10".toBits(), QuantumAlgorithm()
                .input(Qubit.One, Qubit.Zero)
                .layer(CNotGate)
                .repeat(2)
                .layer(CNotGate)
                .repeatEnd()
                .layer(CNotGate)
                .run()
                .measureStandardBasisBits()
        )

        // layer           : CNOT
        // repeat layer (2): CNOT, CNOT
        // layer           : CNOT
        assertEquals("10".toBits(), QuantumAlgorithm()
                .input(Qubit.One, Qubit.Zero)
                .layer(CNotGate)
                .repeat(2)
                .layer(CNotGate)
                .layer(CNotGate)
                .repeatEnd()
                .layer(CNotGate)
                .run()
                .measureStandardBasisBits()
        )

        // layer           : CNOT
        // repeat layer (2): CNOT,
        // layer           : CNOT
        // repeat layer (1): CNOT,
        assertEquals("11".toBits(), QuantumAlgorithm()
                .input(Qubit.One, Qubit.Zero)
                .layer(CNotGate)
                .repeat(2)
                .layer(CNotGate)
                .repeatEnd()
                .layer(CNotGate)
                .repeat(1)
                .layer(CNotGate)
                .repeatEnd()
                .run()
                .measureStandardBasisBits()
        )
    }
}