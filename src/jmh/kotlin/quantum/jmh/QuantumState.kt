package quantum.jmh

import quantum.blocks.Complex

interface QuantumState {
    val size: Int
    operator fun get(index: Int): Complex
}