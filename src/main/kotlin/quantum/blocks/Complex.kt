package quantum.blocks

data class Complex(val real: Double, val image: Double) {
    companion object {
        val zero = Complex(0.0, 0.0)
    }
}