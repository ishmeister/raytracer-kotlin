package com.bhana

class TestPattern : Pattern() {
    override fun patternAt(point: Tuple): Colour = Colour(point.x, point.y, point.z)
}

class TestShape : Shape("TestShape") {
    var savedRay: Ray? = null

    override fun intersectLocal(localRay: Ray): List<Intersection> {
        savedRay = localRay
        return emptyList()
    }

    override fun normalAtLocal(localPoint: Tuple): Tuple = vector(localPoint.x, localPoint.y, localPoint.z)
}

fun defaultWorld(): World {
    val world = World()
    world.lights.add(PointLight(point(-10.0, 10.0, -10.0), WHITE))

    val s1 = Sphere("sphere1")
    s1.material = Material(
        Colour(0.8, 1.0, 0.6),
        diffuse = 0.7,
        specular = 0.2
    )
    world.shapes.add(s1)

    val s2 = Sphere("sphere2")
    s2.transform = scaling(0.5, 0.5, 0.5)
    world.shapes.add(s2)

    return world
}

fun glassSphere(): Sphere =
    Sphere("GlassSphere").apply { material = Material(transparency = 1.0, refractiveIndex = 1.5) }