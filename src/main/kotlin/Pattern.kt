package com.bhana

import kotlin.math.floor

abstract class Pattern {
    var transform: Matrix = identity()
        set(value) {
            field = value
            inverseTransform = value.inverse()
        }

    var inverseTransform: Matrix = transform.inverse()
        private set

    abstract fun patternAt(point: Tuple): Colour

    fun patternAtShape(shape: Shape, worldPoint: Tuple): Colour {
        val objectPoint = shape.inverseTransform * worldPoint
        val patternPoint = inverseTransform * objectPoint
        return patternAt(patternPoint)
    }
}

data class StripePattern(val a: Colour, val b: Colour) : Pattern() {
    override fun patternAt(point: Tuple): Colour = if (floor(point.x) % 2.0 == 0.0) a else b
}

data class GradientPattern(val a: Colour, val b: Colour) : Pattern() {
    override fun patternAt(point: Tuple): Colour = a + (b - a) * (point.x - floor(point.x))
}