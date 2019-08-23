package com.bhana

fun defaultWorld(): World {
    val world = World()
    world.lights.add(PointLight(point(-10.0, 10.0, -10.0), WHITE))

    val s1 = Sphere("s1")
    s1.material = Material(Colour(0.8, 1.0, 0.6), diffuse = 0.7, specular = 0.2)
    world.objects.add(s1)

    val s2 = Sphere("s2")
    s2.transform = scaling(0.5, 0.5, 0.5)
    world.objects.add(s2)

    return world
}

class World {
    val objects = mutableListOf<Shape>()
    val lights = mutableListOf<PointLight>()

    fun intersect(worldRay: Ray): List<Intersection> = objects.map { o -> o.intersect(worldRay) }.flatten().sorted()
}