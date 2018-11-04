package quantum.blocks

import quantum.blocks.Complex.Companion.complex

data class Complex private constructor(val real: Double, val imaginary: Double) {

    constructor(real: Double) : this(real, 0.0)

    companion object {
        val Zero = Complex(0.0)
        val One = Complex(1.0)
        val I = Complex(0.0, 1.0)
        fun complex(real: Double) = complex(real, 0.0)
        fun complex(real: Double, imaginary: Double) = when {
            real == 0.0 && imaginary == 0.0 -> Zero
            real == 1.0 && imaginary == 0.0 -> One
            real == 0.0 && imaginary == 1.0 -> I
            else -> {
                fun isMinusZero(d: Double) = java.lang.Double.compare(d, -0.0) == 0
                fun avoidMinusZero(d: Double) = if (isMinusZero(d)) 0.0 else d

                val re = avoidMinusZero(real)
                val im = avoidMinusZero(imaginary)
                Complex(re, im)
            }
        }
    }

    operator fun plus(other: Double): Complex = complex(real + other, imaginary)
    operator fun minus(other: Double): Complex = complex(real - other, imaginary)
    operator fun times(other: Double): Complex = complex(real * other, imaginary * other)
    operator fun div(other: Double): Complex = complex(real / other, imaginary / other)

    operator fun unaryMinus() = complex(-real, -imaginary)
    operator fun plus(other: Complex): Complex = complex(real + other.real, imaginary + other.imaginary)
    operator fun times(other: Complex): Complex = complex(
            real * other.real - imaginary * other.imaginary,
            real * other.imaginary + imaginary * other.real)

    fun sqr(): Double = real * real + imaginary * imaginary
    fun norm(): Double = kotlin.math.sqrt(sqr())
}

fun Double.toComplex() = Complex(this)

operator fun Double.plus(c: Complex) = complex(this + c.real, c.imaginary)
operator fun Double.minus(c: Complex) = complex(this - c.real, -c.imaginary)
operator fun Double.times(c: Complex) = complex(this * c.real, this * c.imaginary)