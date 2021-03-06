package quantum.core

import quantum.core.Complex.Companion.complex
import java.util.*

data class Complex private constructor(val real: Double, val imaginary: Double = 0.00) {

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

    fun conjugate() = complex(real, -imaginary)

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

    infix fun scalar(other: Complex) = this.conjugate() * other

    override fun toString() = buildString {

        fun format(d: Double) = String.format(Locale.US, "%.2f", d);
        fun imageToString() = if (imaginary == 1.0) "i" else "${format(imaginary)}i"

        if (real == 0.0) {
            if (imaginary == 0.0) {
                append(0.0)
            } else {
                append(imageToString())
            }
        } else {
            append(format(real))
            if (imaginary != 0.0) {
                if (imaginary > 0.0) {
                    append('+')
                }
                append(imageToString())
            }
        }
    }
}

fun Double.toComplex() = complex(this)

operator fun Double.plus(c: Complex) = complex(this + c.real, c.imaginary)
operator fun Double.minus(c: Complex) = complex(this - c.real, -c.imaginary)
operator fun Double.times(c: Complex) = complex(this * c.real, this * c.imaginary)