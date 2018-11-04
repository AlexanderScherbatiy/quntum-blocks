package quantum.blocks

enum class Bit {
    Zero,
    One;

    fun isZero() = this == Zero
    fun inverse() = !this

    operator fun not() = when (this) {
        Zero -> One
        One -> Zero
    }

    operator fun plus(other: Bit) = when (this) {
        Zero -> other
        One -> !other
    }

    operator fun times(other: Bit) = when (this) {
        Zero -> Zero
        One -> other
    }

    companion object {
        fun toBits(i: Int) = Integer
                .toBinaryString(i)
                .map { if (it == '0') Zero else One }
    }
}

