package quantum.blocks

import java.util.*

val ComplexZero = Complex(0.0)
val ComplexOne = Complex(1.0)
val ComplexI = Complex(0.0, 1.0)

data class Complex(val real: Double, val imaginary: Double) {

    constructor(real: Double) : this(real, 0.0)


    operator fun plus(other: Double): Complex = Complex(real + other, imaginary)
    operator fun minus(other: Double): Complex = Complex(real - other, imaginary)
    operator fun times(other: Double): Complex = Complex(real * other, imaginary * other)
    operator fun div(other: Double): Complex = Complex(real / other, imaginary / other)

    operator fun unaryMinus() = Complex(-real, -imaginary)
    operator fun plus(other: Complex): Complex = Complex(real + other.real, imaginary + other.imaginary)
    operator fun times(other: Complex): Complex = Complex(
            real * other.real - imaginary * other.imaginary,
            real * other.imaginary + imaginary * other.real)


    fun sqr(): Double = real * real + imaginary * imaginary
    fun norm(): Double = kotlin.math.sqrt(sqr())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Complex) return false
        return real == other.real && imaginary == other.imaginary
    }

    override fun hashCode(): Int {

        fun isNegativeZero(d: Double) = java.lang.Double.compare(d, -0.0) == 0
        fun excludeNegativeZero(d: Double) = if (isNegativeZero(d)) 0.0 else d
        fun hash(d: Double) = java.lang.Double.hashCode(excludeNegativeZero(d))

        return 31 * hash(real) + hash(imaginary)
    }
}

fun Double.toComplex() = Complex(this)

operator fun Double.plus(c: Complex) = Complex(this + c.real, c.imaginary)
operator fun Double.minus(c: Complex) = Complex(this - c.real, -c.imaginary)
operator fun Double.times(c: Complex) = Complex(this * c.real, this * c.imaginary)
