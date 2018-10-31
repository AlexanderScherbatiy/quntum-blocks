package quantum.blocks

data class Qubit private constructor(val zero: Complex, val one: Complex) {

    companion object {
        fun fromComplex(zero: Complex, one: Complex): Qubit = Qubit(zero, one)
    }
}