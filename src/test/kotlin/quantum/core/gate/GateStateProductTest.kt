package quantum.core.gate

import org.junit.Test
import quantum.core.Qubit
import quantum.gate.hadamar
import quantum.gate.identity
import quantum.util.assertStateEquals

class GateStateProductTest {

    @Test
    fun testIdentityProduct() {

        val identity = identity()
        assertStateEquals(Qubit.Zero, identity * Qubit.Zero)
        assertStateEquals(Qubit.One, identity * Qubit.One)
        assertStateEquals(Qubit.Plus, identity * Qubit.Plus)
        assertStateEquals(Qubit.Minus, identity * Qubit.Minus)
    }

    @Test
    fun testHadamarProduct() {

        val hadamar = hadamar()
        assertStateEquals(Qubit.Plus, hadamar * Qubit.Zero)
        assertStateEquals(Qubit.Minus, hadamar * Qubit.One)
        assertStateEquals(Qubit.Zero, hadamar * Qubit.Plus)
        assertStateEquals(Qubit.One, hadamar * Qubit.Minus)
    }
}