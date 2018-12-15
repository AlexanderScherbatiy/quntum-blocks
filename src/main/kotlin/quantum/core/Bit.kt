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

class Bits(vararg val bits: Bit) {

    fun slice(vararg indices: Int): Bits =
            Bits(* indices.map { bits[it] }.toTypedArray())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Bits
        return bits.contentEquals(other.bits)
    }

    override fun hashCode(): Int {
        return bits.contentHashCode()
    }

    override fun toString() = "Bits(${bits.contentToString()})"
}

fun Boolean.toBit() = if (this) Bit.One else Bit.Zero

fun Int.toBits() = Integer
        .toBinaryString(this).toBits()

fun String.toBits() = map { if (it == '0') Bit.Zero else Bit.One }

fun Int.toBitsArray(size: Int): Bits {
    return Integer.toBinaryString(this).padStart(size, '0').toBitsArray()
}

fun String.toBitsArray() = Bits(* Array(length) {
    if (this[it] == '0') Bit.Zero else Bit.One
})