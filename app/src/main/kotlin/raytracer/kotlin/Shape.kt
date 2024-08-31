package raytracer.kotlin

abstract class Shape(val id: String) {
    var material: Material = Material()

    var transform: Matrix = identity()
        set(value) {
            field = value
            inverseTransform = value.inverse()
        }

    var inverseTransform: Matrix = transform.inverse()
        private set

    fun normalAt(worldPoint: Tuple): Tuple {
        val localPoint = inverseTransform * worldPoint
        val localNormal = normalAtLocal(localPoint)
        val worldNormal = inverseTransform.transpose() * localNormal
        return vector(worldNormal.x, worldNormal.y, worldNormal.z).normalise()
    }

    abstract fun normalAtLocal(localPoint: Tuple): Tuple

    fun intersect(worldRay: Ray): List<Intersection> {
        val localRay = worldRay.transform(inverseTransform)
        return intersectLocal(localRay)
    }

    internal abstract fun intersectLocal(localRay: Ray): List<Intersection>
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Shape

        if (id != other.id) return false
        if (material != other.material) return false
        if (transform != other.transform) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + material.hashCode()
        result = 31 * result + transform.hashCode()
        return result
    }

    override fun toString(): String {
        return "Shape(id='$id', material=$material, transform=$transform)"
    }
}