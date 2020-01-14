package com.bhana

import kotlin.math.floor

class StripePattern(val a: Colour, val b: Colour) {
    fun stripeAt(point: Tuple): Colour = if (floor(point.x) % 2.0 == 0.0) a else b
}