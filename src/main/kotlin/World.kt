package com.bhana

fun defaultWorld(): World {
    val world = World()
    world.lights.add(PointLight(point(-10.0, 10.0, -10.0), WHITE))

    val s1 = Sphere("s1")
    s1.material = Material(Colour(0.8, 1.0, 0.6), diffuse = 0.7, specular = 0.2)
    world.shapes.add(s1)

    val s2 = Sphere("s2")
    s2.transform = scaling(0.5, 0.5, 0.5)
    world.shapes.add(s2)

    return world
}

class World {
    val shapes = mutableListOf<Shape>()
    val lights = mutableListOf<PointLight>()

    fun intersect(worldRay: Ray): List<Intersection> = shapes.map { o -> o.intersect(worldRay) }
        .flatten().filter { i -> i.t >= 0 }.sorted()

    fun shadeHit(comps: Computations) =
        lights.map { l ->
            l.lighting(
                comps.shape.material,
                comps.shape,
                comps.point,
                comps.eyeVec,
                comps.normalVec,
                isShadowed(comps.overPoint, l)
            )
        }.reduce { sum, colour -> sum + colour }

    fun colourAt(ray: Ray): Colour {
        val intersections = intersect(ray)

        return if (intersections.isEmpty()) BLACK
        else {
            val hit = intersections[0]
            val comps = prepareComputations(hit, ray)
            shadeHit(comps)
        }
    }

    fun isShadowed(point: Tuple, light: PointLight): Boolean {
        val vec = light.position - point
        val distance = vec.magnitude()
        val direction = vec.normalise()

        val ray = Ray(point, direction)
        val intersections = intersect(ray)
        val hit = hit(intersections)

        return hit != null && hit.t < distance
    }
}