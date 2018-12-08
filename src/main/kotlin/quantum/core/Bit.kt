package quantum.core

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

    override fun toString() = when (this) {
        Zero -> "0"
        One -> "1"
    }
}

fun Boolean.toBit() = if (this) Bit.One else Bit.Zero

fun Int.toBits() = Integer
        .toBinaryString(this).toBits()

fun String.toBits() = map { if (it == '0') Bit.Zero else Bit.One }