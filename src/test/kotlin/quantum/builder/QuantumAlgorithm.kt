package quantum.builder

import quantum.core.Complex
import quantum.core.QuantumGate
import quantum.core.QuantumState
import quantum.core.Qubit

class QuantumAlgorithm {

    var inputs: Array<out Qubit>? = null
    var gates: Array<out QuantumGate>? = null
    var measurementBasis: Array<out QuantumState>? = null


    fun inputs(vararg qubits: Qubit): Gates {
        this.inputs = qubits
        return Gates()
    }

    inner class Gates {
        fun gates(vararg gates: QuantumGate): Measurement {
            this@QuantumAlgorithm.gates = gates
            return Measurement()
        }
    }

    inner class Measurement {
        fun measure(vararg basis: QuantumState): Result {
            this@QuantumAlgorithm.measurementBasis = basis
            return Result()
        }
    }

    inner class Result {
        fun result(): List<Complex> {
            val inputState = inputs!!
                    .reduce<QuantumState, QuantumState> { q1, q2 -> q1.tensorProduct(q2) }
            val gate = gates!!
                    .reduce { gate1, gate2 -> gate1.tensorProduct(gate2) }
            val outputState = gate * inputState
            return measurementBasis!!.map { it * outputState }
        }
    }
}