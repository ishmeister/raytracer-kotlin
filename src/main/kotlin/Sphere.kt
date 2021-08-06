package com.kotrt

import kotlin.math.sqrt

// All spheres are unit spheres centered at origin
private const val RADIUS = 1.0
private val CENTER = ORIGIN

class Sphere(id: String) : Shape(id) {
    override fun intersectLocal(localRay: Ray): List<Intersection> {
        val sphereToRay = localRay.origin - CENTER
        val a = localRay.direction.dot(localRay.direction)
        val b = 2.0 * localRay.direction.dot(sphereToRay)
        val c = sphereToRay.dot(sphereToRay) - RADIUS * RADIUS
        val discriminant = b * b - 4.0 * a * c

        if (discriminant < 0.0) {
            return emptyList()
        }

        val t1 = (-b - sqrt(discriminant)) / (2.0 * a)
        val t2 = (-b + sqrt(discriminant)) / (2.0 * a)

        return arrayListOf(Intersection(t1, this), Intersection(t2, this))
    }

    override fun normalAtLocal(localPoint: Tuple): Tuple {
        val localNormal = localPoint - CENTER
        return vector(localNormal.x, localNormal.y, localNormal.z)
    }
}