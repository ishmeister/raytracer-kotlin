package com.bhana

import kotlin.math.floor
import kotlin.math.sqrt

abstract class Pattern {
    var transform: Matrix = identity()
        set(value) {
            field = value
            inverseTransform = value.inverse()
        }

    private var inverseTransform: Matrix = transform.inverse()

    abstract fun patternAt(point: Tuple): Colour

    fun patternAtShape(shape: Shape, worldPoint: Tuple): Colour {
        val objectPoint = shape.inverseTransform * worldPoint
        val patternPoint = inverseTransform * objectPoint
        return patternAt(patternPoint)
    }
}

class StripePattern(val a: Colour, val b: Colour) : Pattern() {
    override fun patternAt(point: Tuple): Colour = when {
        floor(point.x) % 2.0 == 0.0 -> a
        else -> b
    }
}

class GradientPattern(val a: Colour, val b: Colour) : Pattern() {
    override fun patternAt(point: Tuple): Colour = a + (b - a) * (point.x - floor(point.x))
}

class RingPattern(val a: Colour, val b: Colour) : Pattern() {
    override fun patternAt(point: Tuple): Colour = when {
        floor(sqrt(point.x * point.x + point.z * point.z)) % 2.0 == 0.0 -> a
        else -> b
    }
}

class CheckerPattern(val a: Colour, val b: Colour) : Pattern() {
    override fun patternAt(point: Tuple): Colour = when {
        (floor(point.x) + floor(point.y) + floor(point.z)) % 2.0 == 0.0 -> a
        else -> b
    }
}
