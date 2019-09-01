package com.bhana

import kotlin.math.sqrt

// All spheres are unit spheres centered at origin
const val radius = 1.0
val center = ORIGIN

class Sphere(id: String) : Shape(id) {

    override fun intersect(worldRay: Ray): List<Intersection> {
        val ray = worldRay.transform(inverseTransform)

        val sphereToRay = ray.origin - center
        val a = ray.direction.dot(ray.direction)
        val b = 2.0 * ray.direction.dot(sphereToRay)
        val c = sphereToRay.dot(sphereToRay) - radius * radius
        val discriminant = b * b - 4.0 * a * c

        if (discriminant < 0.0) {
            return emptyList()
        }

        val t1 = (-b - sqrt(discriminant)) / (2.0 * a)
        val t2 = (-b + sqrt(discriminant)) / (2.0 * a)

        return arrayListOf(Intersection(t1, this), Intersection(t2, this))
    }

    override fun normalAt(worldPoint: Tuple): Tuple {
        val objectPoint = inverseTransform * worldPoint
        val objectNormal = objectPoint - center
        val worldNormal = inverseTransform.transpose() * objectNormal
        return vector(worldNormal.x, worldNormal.y, worldNormal.z).normalise()
    }
}