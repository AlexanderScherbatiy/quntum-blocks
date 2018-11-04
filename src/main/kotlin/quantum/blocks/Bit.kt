package quantum.blocks

enum class Bit {
    Zero,
    One;

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