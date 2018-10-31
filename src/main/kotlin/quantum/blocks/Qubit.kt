package quantum.blocks

data class Qubit private constructor(val zero: Complex, val one: Complex) {

    companion object {
        fun fromComplex(zero: Complex, one: Complex): Qubit {

            val sqr = zero.sqr() + one.sqr()

            if(sqr == 1.0){
                return Qubit(zero, one)
            } else {
                val r = kotlin.math.sqrt(sqr)
                return Qubit(zero / r, one / r)
            }

        }
    }
}