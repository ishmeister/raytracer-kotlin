package com.bhana

import kotlin.math.sqrt

data class Tuple(val x: Double = 0.0, val y: Double = 0.0, val z: Double = 0.0, val w: Double = 0.0) {

    fun magnitude(): Double = sqrt(x * x + y * y + z * z)

    fun normalise(): Tuple {
        val m = magnitude()
        return Tuple(x / m, y / m, z / m, w / m)
    }

    fun dot(other: Tuple): Double = x * other.x + y * other.y + z * other.z + w * other.w

    fun cross(other: Tuple): Tuple =
        Tuple(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x)

    fun isPoint() = w == 1.0
    fun isVector() = w == 0.0

    operator fun plus(other: Tuple) = Tuple(x + other.x, y + other.y, z + other.z, w + other.w)
    operator fun minus(other: Tuple) = Tuple(x - other.x, y - other.y, z - other.z, w - other.w)
    operator fun unaryMinus() = Tuple(-x, -y, -z, -w)
    operator fun times(scalar: Double) = Tuple(x * scalar, y * scalar, z * scalar, w * scalar)
    operator fun div(scalar: Double) = Tuple(x / scalar, y / scalar, z / scalar, w / scalar)

    fun reflect(normal: Tuple): Tuple = this - normal * 2.0 * dot(normal)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Tuple
        return x.eq(other.x) && y.eq(other.y) && z.eq(other.z) && w.eq(other.w)
    }
}
