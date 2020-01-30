package com.bhana

class Ray(val origin: Tuple, val direction: Tuple) {
    init {
        require(origin.isPoint()) { "invalid ray: origin must be a point $origin" }
        require(direction.isVector()) { "invalid ray: direction must be a vector $direction" }
    }

    fun position(t: Double): Tuple = origin + direction * t
    fun transform(m: Matrix): Ray = Ray(m * origin, m * direction)
}