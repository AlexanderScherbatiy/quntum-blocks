package quantum.builder

import quantum.core.*

class QuantumAlgorithm {

    var inputs: Array<out Qubit>? = null
    val layers: MutableList<Array<out QuantumGate>> = mutableListOf()

    fun inputs(vararg qubits: Qubit): GateLayer {
        this.inputs = qubits
        return GateLayer()
    }

    inner class GateLayer {
        fun gatesLayer(vararg gates: QuantumGate): GateLayer {
            this@QuantumAlgorithm.layers += gates
            return this
        }

        fun run(): Result {
            return Result()
        }
    }

    inner class Result {

        val outputState: QuantumState

        init {
            var state = inputs!!.reduce { q1: QuantumState, q2: QuantumState ->
                q1 tensorProduct q2
            }
            outputState = layers.fold(state) { s, gates -> tensorProduct(*gates) * s }
        }

        fun measureStandardBasisBits() =
                outputState.measureBasisIndex().toBits()
    }
}