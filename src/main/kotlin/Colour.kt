package com.bhana

class Colour(val r: Double = 0.0, val g: Double = 0.0, val b: Double = 0.0) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        other as Colour
        return feq(r, other.r) && feq(g, other.g) && feq(b, other.b)
    }

    override fun hashCode(): Int {
        var result = r.hashCode()
        result = 31 * result + g.hashCode()
        result = 31 * result + b.hashCode()
        return result
    }

    override fun toString(): String = "Colour(r=$r, g=$g, b=$b)"
}

operator fun Colour.plus(other: Colour) = Colour(r + other.r, g + other.g, b + other.b)
operator fun Colour.minus(other: Colour) = Colour(r - other.r, g - other.g, b - other.b)
operator fun Colour.times(scalar: Double) = Colour(r * scalar, g * scalar, b * scalar)
operator fun Colour.times(other: Colour) = Colour(r * other.r, g * other.g, b * other.b)


