package com.bhana

abstract class Shape(val id: String) {
    abstract fun intersect(ray: Ray): List<Intersection>
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
}