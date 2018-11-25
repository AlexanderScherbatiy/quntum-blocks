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
}

fun Int.toBits() = Integer
        .toBinaryString(this)
        .map { if (it == '0') Bit.Zero else Bit.One }
