package quantum.builder

import quantum.core.*
import java.util.logging.*


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
    (RepeatGateLayer, repeatEnd())-> GateLayer
    (GateLayer, run)-> Result
     */

    lateinit var inputs: Array<out Qubit>
    val layers = mutableListOf<AbstractGateLayerItem>()
    private var layerCount = 0

    fun logLevel(level: String): QuantumAlgorithm {
        val logLevel = Level.parse(level.toUpperCase())
        val consoleHandler = ConsoleHandler()
        consoleHandler.level = logLevel
        consoleHandler.formatter = LogFormatter
        LOG.addHandler(consoleHandler)
        LOG.level = logLevel
        return this
    }

    private fun debug(msg: () -> String) {
        LOG.fine(msg())
    }

    private fun trace(msg: () -> String) {
        LOG.finer(msg())
    }

    fun input(vararg qubits: Qubit): GateLayer {
        this.inputs = qubits
        debug { "input: ${qubits.contentToString()}" }
        return GateLayer(layers)
    }

    abstract inner class AbstractGateLayerItem {

        abstract fun calculate(state: QuantumState): QuantumState
    }

    inner class GateLayerItem(
            val num: Int, val gates: Array<out QuantumGate>
    ) : AbstractGateLayerItem() {

        init {
            debug { "layer[$num]: ${gates.contentToString()}" }
        }

        override fun calculate(state: QuantumState): QuantumState {
            val gate = tensorProduct(*gates)
            trace { "layer[$num] gate : $gate" }
            val result = gate * state
            trace { "layer[$num] state: $state -> $result" }
            return result
        }
    }

    inner class RepeatGateLayerItem(val times: Int) : AbstractGateLayerItem() {

        val layers = mutableListOf<AbstractGateLayerItem>()

        override fun calculate(state: QuantumState): QuantumState {
            var result = state
            for (i in 1..times) {
                layers.forEach {
                    trace { "repeat $i of $times" }
                    result = it.calculate(result)
                }
            }
            return result
        }
    }


    inner class GateLayer(val layers: MutableList<AbstractGateLayerItem>) {

        fun layer(vararg gates: QuantumGate): GateLayer {
            layers += GateLayerItem(++layerCount, gates)
            return GateLayer(layers)
        }

        fun repeat(times: Int): RepeatGateLayer {
            val repeatLayerItem = RepeatGateLayerItem(times)
            layers += repeatLayerItem

            debug { "repeat layer: $times" }
            return RepeatGateLayer(repeatLayerItem.layers, layers)
        }

        fun run(): Result {
            return Result()
        }
    }

    inner class RepeatGateLayer(val layers: MutableList<AbstractGateLayerItem>,
                                val parentLayers: MutableList<AbstractGateLayerItem>) {

        fun layer(vararg gates: QuantumGate): RepeatGateLayer {
            layers += GateLayerItem(++layerCount, gates)
            return RepeatGateLayer(layers, parentLayers)
        }

        fun repeatEnd(): GateLayer {
            debug { "repeat end" }
            return GateLayer(parentLayers)
        }
    }

    inner class Result {

        val outputState: QuantumState

        init {

            val state = inputs.reduce { q1: QuantumState, q2: QuantumState ->
                q1 tensorProduct q2
            }
            trace { "input state : $state" }
            outputState = layers.fold(state) { s, layer -> layer.calculate(s) }
            debug { "output state: $outputState" }
        }

        fun measureStandardBasisBits(): List<Bit> {
            val bits = outputState.measureBasisIndex().toBits()
            trace { "measure standard basis bits: $bits" }
            return bits
        }
    }

    object LogFormatter : Formatter() {

        private fun levelToString(record: LogRecord) = when (record.level) {
            Level.INFO -> "info"
            Level.FINE -> "debug"
            Level.FINER -> "trace"
            else -> "unknown"
        }

        override fun format(record: LogRecord) =
                "[quantum algorithm] ${levelToString(record)} ${formatMessage(record)}\n"
    }
}