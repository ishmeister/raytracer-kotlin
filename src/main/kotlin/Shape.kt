package com.bhana

abstract class Shape(val id: String) {

    var material: Material = Material()

    var transform: Matrix = identity()
        set(value) {
            field = value
            inverseTransform = value.inverse()
        }

    protected var inverseTransform: Matrix = transform.inverse()

    abstract fun intersect(worldRay: Ray): List<Intersection>
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

    abstract fun normalAt(worldPoint: Tuple): Tuple

}