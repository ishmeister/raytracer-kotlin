package com.bhana

import java.util.*

data class Intersection(val t: Double, val shape: Shape) : Comparable<Intersection> {
    override fun compareTo(other: Intersection) = when {
        t < other.t -> -1
        t > other.t -> 1
        else -> 0
    }

    private fun findRefractiveIndexes(xs: List<Intersection>): Pair<Double, Double> {
        val containers = LinkedList<Shape>()
        var n1 = 1.0
        var n2 = 1.0

        xs.forEach { i ->
            if (i == this) {
                n1 = if (containers.isEmpty()) 1.0
                else containers[containers.size - 1].material.refractiveIndex
            }

            if (containers.contains(i.shape)) containers.remove(i.shape)
            else containers.add(i.shape)

            if (i == this) {
                n2 = if (containers.isEmpty()) 1.0
                else containers[containers.size - 1].material.refractiveIndex
            }
        }

        return Pair(n1, n2)
    }

    fun prepareComputations(ray: Ray, xs: List<Intersection> = listOf(this)): HitComputations {
        val (n1, n2) = findRefractiveIndexes(xs)

        val point = ray.position(t)
        val eyeVec = -ray.direction
        val normalVec = shape.normalAt(point)

        val inside = normalVec.dot(eyeVec) < 0.0
        val computedNormalVec = if (inside) -normalVec else normalVec

        // push point slightly in direction of normal to prevent self-shadowing
        val overPoint = point + computedNormalVec * EPSILON
        val reflectVec = ray.direction.reflect(computedNormalVec)

        return HitComputations(
            t = t,
            shape = shape,
            point = point,
            overPoint = overPoint,
            eyeVec = eyeVec,
            normalVec = computedNormalVec,
            inside = inside,
            reflectVec = reflectVec,
            n1 = n1,
            n2 = n2
        )
    }
}

data class HitComputations(
    val t: Double,
    val shape: Shape,
    val point: Tuple,
    val overPoint: Tuple,
    val eyeVec: Tuple,
    val normalVec: Tuple,
    val inside: Boolean,
    val reflectVec: Tuple,
    val n1: Double,
    val n2: Double
)

fun findHit(intersections: List<Intersection>): Intersection? =
    when {
        intersections.isEmpty() -> null
        else -> intersections.sorted().firstOrNull { it.t >= 0.0 }
    }