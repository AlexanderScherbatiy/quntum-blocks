package quantum.blocks

val ComplexZero = Complex(0, 0)
val ComplexOne = Complex(1, 0)
val ComplexI = Complex(0, 1)

data class Complex(val real: Double, val imaginary: Double) {

    constructor(real: Int) : this(real.toDouble())
    constructor(real: Int, imaginary: Int) : this(real.toDouble(), imaginary.toDouble())

    constructor(real: Double) : this(real, 0.0)

    operator fun div(other: Int): Complex = div(other.toDouble())
    operator fun div(other: Double): Complex = Complex(real / other, imaginary / other)

    operator fun plus(other: Complex): Complex = Complex(real + other.real, imaginary + other.imaginary)
    operator fun times(other: Complex): Complex = Complex(
            real * other.real - imaginary * other.imaginary,
            real * other.imaginary + imaginary * other.real)


    fun sqr(): Double = real * real + imaginary * imaginary
    fun norm(): Double = kotlin.math.sqrt(sqr())
}