package com.bhana

class TestShape : Shape("test") {

    var savedRay: Ray? = null

    override fun intersectLocal(localRay: Ray): List<Intersection> {
        savedRay = localRay
        return emptyList()
    }

    override fun normalAtLocal(localPoint: Tuple): Tuple = vector(localPoint.x, localPoint.y, localPoint.z)
}