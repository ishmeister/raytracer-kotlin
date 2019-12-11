package com.bhana

import kotlin.math.abs

// Normal of a plane is constant everywhere
private val NORMAL = vector(0.0, 1.0, 0.0)

class Plane(id: String) : Shape(id) {

    override fun normalAtLocal(localPoint: Tuple): Tuple = NORMAL

    override fun intersectLocal(ray: Ray): List<Intersection> =
        if (abs(ray.direction.y) < EPSILON) emptyList()
        else {
            val t = -ray.origin.y / ray.direction.y
            arrayListOf(Intersection(t, this))
        }

}