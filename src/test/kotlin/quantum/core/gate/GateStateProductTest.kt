package quantum.core.gate

import org.junit.Test
import quantum.core.Complex.Companion.complex
import quantum.core.MatrixQuantumGate
import quantum.core.Qubit
import quantum.core.qubit
import quantum.core.toComplex
import quantum.gate.cnot
import quantum.gate.hadamar
import quantum.gate.identity
import quantum.util.assertStateEquals

class GateStateProductTest {

    @Test
    fun testProductState() {

        val angle = 28 * Math.PI / 180
        val cos = Math.cos(angle).toComplex()
        val sin = Math.sin(angle).toComplex()

        val gate = MatrixQuantumGate(arrayOf(
                arrayOf(cos, -sin),
                arrayOf(sin, cos)
        ))

        assertStateEquals(qubit(cos, sin), gate * Qubit.Zero)
        assertStateEquals(qubit(-sin, cos), gate * Qubit.One)
        assertStateEquals(qubit(cos - sin, sin + cos), gate * Qubit.Plus)
        assertStateEquals(qubit(cos + sin, sin - cos), gate * Qubit.Minus)
    }
}