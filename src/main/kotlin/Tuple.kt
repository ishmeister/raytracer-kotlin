package com.bhana

import kotlin.math.abs

const val EPSILON: Double = 0.00001

fun feq(f1: Double, f2: Double): Boolean = abs(f1 - f2) < EPSILON

open class Tuple(val x: Double, val y: Double, val z: Double, val w: Double) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        other as Tuple
        return feq(this.x, other.x) && feq(this.y, other.y) && feq(this.z, other.z) && feq(this.w, other.w)
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        result = 31 * result + w.hashCode()
        return result
    }

    override fun toString(): String {
        return "Tuple(x=$x, y=$y, z=$z, w=$w)"
    }
}

class Point(x: Double, y: Double, z: Double, w: Double = 1.0) : Tuple(x, y, z, w)
class Vector(x: Double, y: Double, z: Double, w: Double = 0.0) : Tuple(x, y, z, w)

operator fun Tuple.plus(other: Tuple) = Tuple(x + other.x, y + other.y, z + other.z, w + other.w)
operator fun Tuple.minus(other: Tuple) = Tuple(x - other.x, y - other.y, z - other.z, w - other.w)
operator fun Tuple.unaryMinus() = Tuple(-x, -y, -z, -w)
operator fun Tuple.times(scalar: Double) = Tuple(x * scalar, y * scalar, z * scalar, w * scalar)


