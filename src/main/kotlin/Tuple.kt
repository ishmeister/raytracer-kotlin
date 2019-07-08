package com.bhana

import kotlin.math.abs
import kotlin.math.sqrt

const val EPSILON: Double = 0.00001

fun feq(f1: Double, f2: Double): Boolean = abs(f1 - f2) < EPSILON

fun vector(x: Double, y: Double, z: Double): Tuple = Tuple(x, y, z, 0.0)
fun point(x: Double, y: Double, z: Double): Tuple = Tuple(x, y, z, 1.0)

open class Tuple(val x: Double = 0.0, val y: Double = 0.0, val z: Double = 0.0, val w: Double = 0.0) {

    fun magnitude(): Double = sqrt(x * x + y * y + z * z)

    fun normalise(): Tuple {
        val m = magnitude()
        return Tuple(x / m, y / m, z / m, w / m)
    }

    fun dot(other: Tuple): Double = x * other.x + y * other.y + z * other.z + w * other.w

    fun cross(other: Tuple): Tuple =
        Tuple(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Tuple
        return feq(x, other.x) && feq(y, other.y) && feq(z, other.z) && feq(w, other.w)
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        result = 31 * result + w.hashCode()
        return result
    }

    override fun toString(): String = "Tuple(x=$x, y=$y, z=$z, w=$w)"
    operator fun plus(other: Tuple) = Tuple(x + other.x, y + other.y, z + other.z, w + other.w)
    operator fun minus(other: Tuple) = Tuple(x - other.x, y - other.y, z - other.z, w - other.w)
    operator fun unaryMinus() = Tuple(-x, -y, -z, -w)
    operator fun times(scalar: Double) = Tuple(x * scalar, y * scalar, z * scalar, w * scalar)
    operator fun div(scalar: Double) = Tuple(x / scalar, y / scalar, z / scalar, w / scalar)
}



