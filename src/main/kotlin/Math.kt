package com.bhana

import kotlin.math.*

const val EPSILON = 0.00001
val ORIGIN = point(0.0, 0.0, 0.0)

fun Double.eq(other: Double) = abs(this - other) < EPSILON

fun clamp(value: Double, min: Double, max: Double) = max(min, min(value, max))
fun radians(deg: Double) = (deg / 180) * PI

fun vector(x: Double, y: Double, z: Double) = Tuple(x, y, z, 0.0)
fun point(x: Double, y: Double, z: Double) = Tuple(x, y, z, 1.0)

fun identity() = Matrix(
    4, arrayOf(
        doubleArrayOf(1.0, 0.0, 0.0, 0.0),
        doubleArrayOf(0.0, 1.0, 0.0, 0.0),
        doubleArrayOf(0.0, 0.0, 1.0, 0.0),
        doubleArrayOf(0.0, 0.0, 0.0, 1.0)
    )
)

fun translation(x: Double, y: Double, z: Double) = Matrix(
    4, arrayOf(
        doubleArrayOf(1.0, 0.0, 0.0, x),
        doubleArrayOf(0.0, 1.0, 0.0, y),
        doubleArrayOf(0.0, 0.0, 1.0, z),
        doubleArrayOf(0.0, 0.0, 0.0, 1.0)
    )
)

fun scaling(x: Double, y: Double, z: Double) = Matrix(
    4, arrayOf(
        doubleArrayOf(x, 0.0, 0.0, 0.0),
        doubleArrayOf(0.0, y, 0.0, 0.0),
        doubleArrayOf(0.0, 0.0, z, 0.0),
        doubleArrayOf(0.0, 0.0, 0.0, 1.0)
    )
)

fun rotationX(r: Double) = Matrix(
    4, arrayOf(
        doubleArrayOf(1.0, 0.0, 0.0, 0.0),
        doubleArrayOf(0.0, cos(r), -sin(r), 0.0),
        doubleArrayOf(0.0, sin(r), cos(r), 0.0),
        doubleArrayOf(0.0, 0.0, 0.0, 1.0)
    )
)

fun rotationY(r: Double) = Matrix(
    4, arrayOf(
        doubleArrayOf(cos(r), 0.0, sin(r), 0.0),
        doubleArrayOf(0.0, 1.0, 0.0, 0.0),
        doubleArrayOf(-sin(r), 0.0, cos(r), 0.0),
        doubleArrayOf(0.0, 0.0, 0.0, 1.0)
    )
)

fun rotationZ(r: Double) = Matrix(
    4, arrayOf(
        doubleArrayOf(cos(r), -sin(r), 0.0, 0.0),
        doubleArrayOf(sin(r), cos(r), 0.0, 0.0),
        doubleArrayOf(0.0, 0.0, 1.0, 0.0),
        doubleArrayOf(0.0, 0.0, 0.0, 1.0)
    )
)

fun shearing(xy: Double, xz: Double, yx: Double, yz: Double, zx: Double, zy: Double) = Matrix(
    4, arrayOf(
        doubleArrayOf(1.0, xy, xz, 0.0),
        doubleArrayOf(yx, 1.0, yz, 0.0),
        doubleArrayOf(zx, zy, 1.0, 0.0),
        doubleArrayOf(0.0, 0.0, 0.0, 1.0)
    )
)

fun view(from: Tuple, to: Tuple, up: Tuple): Matrix {
    val forward = (to - from).normalise()
    val upN = up.normalise()
    val left = forward.cross(upN)
    val trueUp = left.cross(forward)

    val orientation = Matrix(
        4, arrayOf(
            doubleArrayOf(left.x, left.y, left.z, 0.0),
            doubleArrayOf(trueUp.x, trueUp.y, trueUp.z, 0.0),
            doubleArrayOf(-forward.x, -forward.y, -forward.z, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 1.0)
        )
    )

    return orientation * translation(-from.x, -from.y, -from.z)
}