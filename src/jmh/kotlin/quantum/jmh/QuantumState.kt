package quantum.jmh

import quantum.core.Complex

interface QuantumState {
    val size: Int
    operator fun get(index: Int): Complex
}