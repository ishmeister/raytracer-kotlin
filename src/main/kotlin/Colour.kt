package com.bhana

val BLACK: Colour = Colour(0.0, 0.0, 0.0)
val WHITE: Colour = Colour(1.0, 1.0, 1.0)
val GREY: Colour = Colour(0.8, 0.8, 0.8)
val RED: Colour = Colour(1.0, 0.0, 0.0)
val BLUE: Colour = Colour(0.0, 0.0, 1.0)

data class Colour(val r: Double = 0.0, val g: Double = 0.0, val b: Double = 0.0) {

    fun clip(): Colour =
        if (r in 0.0..1.0 && g in 0.0..1.0 && b in 0.0..1.0) this
        else Colour(clamp(r, 0.0, 1.0), clamp(g, 0.0, 1.0), clamp(b, 0.0, 1.0))

    operator fun plus(other: Colour) = Colour(r + other.r, g + other.g, b + other.b)
    operator fun minus(other: Colour) = Colour(r - other.r, g - other.g, b - other.b)
    operator fun times(scalar: Double) = Colour(r * scalar, g * scalar, b * scalar)
    operator fun times(other: Colour) = Colour(r * other.r, g * other.g, b * other.b)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Colour
        return r.eq(other.r) && g.eq(other.g) && b.eq(other.b)
    }
}
