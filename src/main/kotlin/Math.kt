package com.bhana

import kotlin.math.*

const val EPSILON = 0.00001

fun Double.eq(other: Double): Boolean = abs(this - other) < EPSILON

fun clamp(value: Double, min: Double, max: Double): Double = max(min, min(value, max))
fun radians(deg: Double): Double = (deg / 180) * PI

fun vector(x: Double, y: Double, z: Double): Tuple = Tuple(x, y, z, 0.0)
fun point(x: Double, y: Double, z: Double): Tuple = Tuple(x, y, z, 1.0)

fun identity(): Matrix = Matrix(
    4, arrayOf(
        doubleArrayOf(1.0, 0.0, 0.0, 0.0),
        doubleArrayOf(0.0, 1.0, 0.0, 0.0),
        doubleArrayOf(0.0, 0.0, 1.0, 0.0),
        doubleArrayOf(0.0, 0.0, 0.0, 1.0)
    )
)

fun translation(x: Double, y: Double, z: Double): Matrix = Matrix(
    4, arrayOf(
        doubleArrayOf(1.0, 0.0, 0.0, x),
        doubleArrayOf(0.0, 1.0, 0.0, y),
        doubleArrayOf(0.0, 0.0, 1.0, z),
        doubleArrayOf(0.0, 0.0, 0.0, 1.0)
    )
)

fun scaling(x: Double, y: Double, z: Double): Matrix = Matrix(
    4, arrayOf(
        doubleArrayOf(x, 0.0, 0.0, 0.0),
        doubleArrayOf(0.0, y, 0.0, 0.0),
        doubleArrayOf(0.0, 0.0, z, 0.0),
        doubleArrayOf(0.0, 0.0, 0.0, 1.0)
    )
)

fun rotationX(r: Double): Matrix = Matrix(
    4, arrayOf(
        doubleArrayOf(1.0, 0.0, 0.0, 0.0),
        doubleArrayOf(0.0, cos(r), -sin(r), 0.0),
        doubleArrayOf(0.0, sin(r), cos(r), 0.0),
        doubleArrayOf(0.0, 0.0, 0.0, 1.0)
    )
)