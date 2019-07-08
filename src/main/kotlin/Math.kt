package com.bhana

import kotlin.math.abs

const val EPSILON: Double = 0.00001

fun Double.eq(other: Double): Boolean = abs(this - other) < EPSILON

fun vector(x: Double, y: Double, z: Double): Tuple = Tuple(x, y, z, 0.0)
fun point(x: Double, y: Double, z: Double): Tuple = Tuple(x, y, z, 1.0)

fun matrix(size: Int, vararg elems: Double): Matrix {
    check(size * size == elems.size) { "invalid matrix: size=$size, elems.size=${elems.size}" }
    val m = Matrix(size)

    for (x in 0 until size) {
        for (y in 0 until size) {
            m.elems[x][y] = elems[x * size + y]
        }
    }

    return m
}