package quantum.blocks

data class Complex(val real: Double, val imaginary: Double) {

    constructor(real: Int) : this(real.toDouble())
    constructor(real: Int, imaginary: Int) : this(real.toDouble(), imaginary.toDouble())

    constructor(real: Double) : this(real, 0.0)

    companion object {
        val ZERO = Complex(0)
        val ONE = Complex(1)
    }

    operator fun plus(other: Complex): Complex = Complex(real + other.real, imaginary + other.imaginary)
}