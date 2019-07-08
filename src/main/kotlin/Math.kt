package com.bhana

import kotlin.math.abs

const val EPSILON: Double = 0.00001

fun Double.eq(other: Double): Boolean = abs(this - other) < EPSILON

fun vector(x: Double, y: Double, z: Double): Tuple = Tuple(x, y, z, 0.0)
fun point(x: Double, y: Double, z: Double): Tuple = Tuple(x, y, z, 1.0)