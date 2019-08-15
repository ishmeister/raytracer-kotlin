package com.bhana

data class Intersection(val t: Double, val shape: Shape) : Comparable<Intersection> {

    override fun compareTo(other: Intersection) = when {
        t < other.t -> -1
        t > other.t -> 1
        else -> 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Intersection

        if (!t.eq(other.t)) return false
        if (shape != other.shape) return false

        return true
    }
}

fun hit(intersections: List<Intersection>): Intersection? =
    if (intersections.isEmpty()) null
    else intersections.sorted().firstOrNull { i -> i.t.eq(0.0) || i.t > 0.0 }
