package quantum.builder

import quantum.core.*
import quantum.gate.identity

class QuantumAlgorithm {

    var inputs: Array<out Qubit>? = null
    val layers: MutableList<Array<out QuantumGate>> = mutableListOf()
    var measurementBasis: Array<out QuantumState>? = null


    fun inputs(vararg qubits: Qubit): Gates {
        this.inputs = qubits
        return Gates()
    }

    inner class Gates {
        fun gates(vararg gates: QuantumGate): Measurement {
            this@QuantumAlgorithm.layers += gates
            return Measurement()
        }

        fun gateLayers(): GateLayer {
            return GateLayer()
        }
    }

    inner class GateLayer {
        fun layer(vararg gates: QuantumGate): GateLayer {
            this@QuantumAlgorithm.layers += gates
            return this
        }

        fun end(): Measurement {
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

            var state = inputs!!
                    .reduce<QuantumState, QuantumState> { q1, q2 -> q1 tensorProduct q2 }

            layers.forEach {
                state = tensorProduct(*it) * state
            }

            return measurementBasis!!.map { it * state }
        }
    }
}