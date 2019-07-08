package com.bhana

import kotlin.math.max
import kotlin.math.min

fun clamp(value: Double, min: Double, max: Double): Double = max(min, min(value, max))

class Colour(val r: Double = 0.0, val g: Double = 0.0, val b: Double = 0.0) {

    fun clip(): Colour {
        return if (r in 0.0..1.0 && g in 0.0..1.0 && b in 0.0..1.0) this
        else Colour(clamp(r, 0.0, 1.0), clamp(g, 0.0, 1.0), clamp(b, 0.0, 1.0))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Colour
        return r.eq(other.r) && g.eq(other.g) && b.eq(other.b)
    }

    override fun hashCode(): Int {
        var result = r.hashCode()
        result = 31 * result + g.hashCode()
        result = 31 * result + b.hashCode()
        return result
    }

    override fun toString(): String = "Colour(r=$r, g=$g, b=$b)"
    operator fun plus(other: Colour) = Colour(r + other.r, g + other.g, b + other.b)
    operator fun minus(other: Colour) = Colour(r - other.r, g - other.g, b - other.b)
    operator fun times(scalar: Double) = Colour(r * scalar, g * scalar, b * scalar)
    operator fun times(other: Colour) = Colour(r * other.r, g * other.g, b * other.b)
}




