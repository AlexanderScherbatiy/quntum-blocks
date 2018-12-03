package quantum.builder

import quantum.core.*
import java.util.logging.Level
import java.util.logging.Logger
import java.util.logging.ConsoleHandler


class QuantumAlgorithm {

    companion object {
        private val LOG = Logger.getLogger(QuantumAlgorithm::class.java.name)

        init {
            LOG.level = Level.parse("warning".toUpperCase())
        }
    }

    /*
    Finite State Machine
    (Constructor, input(qubits)) -> GateLayer
    (GateLayer, layer(gates))-> GateLayer
    (GateLayer, repeat(n))-> RepeatGateLayer
    (RepeatGateLayer, layer(gates))-> RepeatGateLayer
    (RepeatGateLayer, end())-> GateLayer
    (GateLayer, run)-> Result
     */

    lateinit var inputs: Array<out Qubit>
    val layers = mutableListOf<GateLayer>()
    private var layerCount = 0

    fun logLevel(level: String): QuantumAlgorithm {

        val logLevel = Level.parse(level.toUpperCase())
        val consoleHandler = ConsoleHandler()
        consoleHandler.level = logLevel
        LOG.addHandler(consoleHandler)
        LOG.level = logLevel
        return this
    }

    fun input(vararg qubits: Qubit): GateLayer {
        this.inputs = qubits
        LOG.fine("input: ${qubits.contentToString()}")
        return GateLayer()
    }

    inner class GateLayer {
        lateinit var gates: Array<out QuantumGate>
        private val num = ++layerCount

        fun layer(vararg gates: QuantumGate): GateLayer {
            this.gates = gates
            LOG.fine { "layer[$num]: ${gates.contentToString()}" }
            this@QuantumAlgorithm.layers += this
            return GateLayer()
        }

        fun run(): Result {
            return Result()
        }
    }

    inner class Result {

        val outputState: QuantumState

        init {

            val state = inputs.reduce { q1: QuantumState, q2: QuantumState ->
                q1 tensorProduct q2
            }
            LOG.finer { "run input state : $state" }
            outputState = layers.fold(state) { s, layer ->
                val gate = tensorProduct(*layer.gates)
                LOG.finer { "run gate : $gate" }
                LOG.finer { "run state: $s" }
                val result = gate * s
                LOG.finer { "run result: $result" }
                result
            }
            LOG.fine { "output state: $outputState" }
        }

        fun measureStandardBasisBits(): List<Bit> {
            val bits = outputState.measureBasisIndex().toBits()
            LOG.finer { "run measure standard basis bits: $bits" }
            return bits
        }
    }
}