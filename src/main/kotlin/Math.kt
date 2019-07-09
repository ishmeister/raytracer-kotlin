package com.bhana

import kotlin.math.abs

const val EPSILON = 0.00001


fun Double.eq(other: Double): Boolean = abs(this - other) < EPSILON

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