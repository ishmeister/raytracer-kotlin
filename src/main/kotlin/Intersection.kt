package com.bhana

data class Intersection(val t: Double, val shape: Shape) : Comparable<Intersection> {

    override fun compareTo(other: Intersection) = when {
        t < other.t -> -1
        t > other.t -> 1
        else -> 0
    }
}

data class Computations(
    val t: Double,
    val shape: Shape,
    val point: Tuple,
    val overPoint: Tuple,
    val eyeVec: Tuple,
    val normalVec: Tuple,
    val inside: Boolean
)

fun prepareComputations(intersection: Intersection, ray: Ray): Computations {
    val point = ray.position(intersection.t)
    val eyeVec = -ray.direction
    val normalVec = intersection.shape.normalAt(point)

    // push point slightly in direction of normal to prevent self-shadowing
    val overPoint = point + normalVec * EPSILON
    val inside = normalVec.dot(eyeVec) < 0

    val computedNormalVec = if (inside) -normalVec else normalVec

    return Computations(
        t = intersection.t,
        shape = intersection.shape,
        point = point,
        overPoint = overPoint,
        eyeVec = eyeVec,
        normalVec = computedNormalVec,
        inside = inside
    )
}

fun hit(intersections: List<Intersection>): Intersection? =
    when {
        intersections.isEmpty() -> null
        else -> intersections.sorted().firstOrNull { it.t.eq(0.0) || it.t > 0.0 }
    }
