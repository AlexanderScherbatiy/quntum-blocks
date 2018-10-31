package quantum.blocks

data class Complex(val real: Double, val imaginary: Double) {
    companion object {
        val ZERO = Complex(0.0, 0.0)
    }

    operator fun plus(other:Complex):Complex = Complex(real + other.real, imaginary + other.imaginary)
}