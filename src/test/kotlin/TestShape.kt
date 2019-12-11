package com.bhana

class TestShape : Shape("test") {

    var savedRay: Ray? = null

    override fun intersectLocal(ray: Ray): List<Intersection> {
        savedRay = ray
        return emptyList()
    }

    override fun normalAtLocal(localPoint: Tuple): Tuple {
        return return vector(localPoint.x, localPoint.y, localPoint.z)
    }

}