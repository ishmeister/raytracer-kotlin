package com.bhana

const val MAX_RECURSIVE_DEPTH = 5

class World {
    val shapes = mutableListOf<Shape>()
    val lights = mutableListOf<PointLight>()

    fun intersect(worldRay: Ray): List<Intersection> =
        shapes.flatMap { it.intersect(worldRay) }.filter { it.t >= 0.0 }.sorted()

    fun shadeHit(comps: HitComputations, remaining: Int = MAX_RECURSIVE_DEPTH): Colour =
        lights.map {
            val surfaceColour = it.lighting(
                comps.shape.material,
                comps.shape,
                comps.overPoint,
                comps.eyeVec,
                comps.normalVec,
                isShadowed(comps.overPoint, it)
            )
            surfaceColour + reflectedColour(comps, remaining)
        }.reduce { sum, colour -> sum + colour }

    fun colourAt(ray: Ray, remaining: Int = MAX_RECURSIVE_DEPTH): Colour {
        val intersections = intersect(ray)

        return if (intersections.isEmpty()) BLACK
        else {
            val hit = intersections[0]
            val comps = hit.prepareComputations(ray)
            shadeHit(comps, remaining)
        }
    }

    fun isShadowed(point: Tuple, light: PointLight): Boolean {
        val vec = light.position - point
        val distance = vec.magnitude()
        val direction = vec.normalise()

        val ray = Ray(point, direction)
        val intersections = intersect(ray)
        val hit = findHit(intersections)

        return hit != null && hit.t < distance
    }

    fun reflectedColour(comps: HitComputations, remaining: Int = MAX_RECURSIVE_DEPTH): Colour {
        return if (remaining <= 0 || comps.shape.material.reflectivity == 0.0) BLACK
        else {
            val reflectedRay = Ray(comps.overPoint, comps.reflectVec)
            val colour = colourAt(reflectedRay, remaining - 1)
            colour * comps.shape.material.reflectivity
        }
    }
}