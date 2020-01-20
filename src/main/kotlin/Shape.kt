package com.bhana

abstract class Shape(val id: String) {

    var material: Material = Material()

    var transform: Matrix = identity()
        set(value) {
            field = value
            inverseTransform = value.inverse()
        }

    var inverseTransform: Matrix = transform.inverse()
        private set

    override fun toString(): String = "Shape(id='$id')"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Shape

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun normalAt(worldPoint: Tuple): Tuple {
        val localPoint = inverseTransform * worldPoint
        val localNormal = normalAtLocal(localPoint)
        var worldNormal = inverseTransform.transpose() * localNormal
        return vector(worldNormal.x, worldNormal.y, worldNormal.z).normalise()
    }

    abstract fun normalAtLocal(localPoint: Tuple): Tuple

    fun intersect(worldRay: Ray): List<Intersection> {
        val localRay = worldRay.transform(inverseTransform)
        return intersectLocal(localRay)
    }

    internal abstract fun intersectLocal(localRay: Ray): List<Intersection>

}