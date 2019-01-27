package quantum.core.gate

import org.junit.Test
import quantum.core.Qubit
import quantum.gate.cnot
import quantum.gate.hadamar
import quantum.gate.identity
import quantum.util.assertStateEquals

class GateStateProductTest {

    @Test
    fun testIdentityProductState() {

        val identity = identity()
        assertStateEquals(Qubit.Zero, identity * Qubit.Zero)
        assertStateEquals(Qubit.One, identity * Qubit.One)
        assertStateEquals(Qubit.Plus, identity * Qubit.Plus)
        assertStateEquals(Qubit.Minus, identity * Qubit.Minus)
    }

    @Test
    fun testHadamarProductState() {

        val hadamar = hadamar()
        assertStateEquals(Qubit.Plus, hadamar * Qubit.Zero)
        assertStateEquals(Qubit.Minus, hadamar * Qubit.One)
        assertStateEquals(Qubit.Zero, hadamar * Qubit.Plus)
        assertStateEquals(Qubit.One, hadamar * Qubit.Minus)
    }

    @Test
    fun testCNotProductState() {

        val cnot = cnot()
        val e1 = (Qubit.Zero tensor Qubit.Zero)
        val e2 = (Qubit.Zero tensor Qubit.One)
        val e3 = (Qubit.One tensor Qubit.Zero)
        val e4 = (Qubit.One tensor Qubit.One)

        assertStateEquals(e1, cnot * e1)
        assertStateEquals(e2, cnot * e2)
        assertStateEquals(e4, cnot * e3)
        assertStateEquals(e3, cnot * e4)
    }
}