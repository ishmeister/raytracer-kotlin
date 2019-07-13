package com.bhana

import kotlin.math.sqrt

class Sphere(id: String) : Shape(id) {

    override fun intersect(ray: Ray): List<Intersection> {
        val sphereToRay = ray.origin - point(0.0, 0.0, 0.0)
        val a = ray.direction.dot(ray.direction)
        val b = 2.0 * ray.direction.dot(sphereToRay)
        val c = sphereToRay.dot(sphereToRay) - 1.0
        val discriminant = b * b - 4.0 * a * c

        if (discriminant < 0.0) {
            return emptyList()
        }

        val t1 = (-b - sqrt(discriminant)) / (2.0 * a)
        val t2 = (-b + sqrt(discriminant)) / (2.0 * a)

        return arrayListOf(Intersection(t1, this), Intersection(t2, this))
    }

}