package com.bhana

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class WorldTests {

    @Test
    fun `Creating a world`() {
        val w = World()
        assertTrue(w.shapes.isEmpty())
        assertTrue(w.lights.isEmpty())
    }

    @Test
    fun `The default world`() {
        val light = PointLight(point(-10.0, 10.0, -10.0), WHITE)
        val s1 = Sphere("s1")
        s1.material = Material(Colour(0.8, 1.0, 0.6), diffuse = 0.7, specular = 0.2)

        val s2 = Sphere("s2")
        s2.transform = scaling(0.5, 0.5, 0.5)

        val w = defaultWorld()

        assertEquals(light, w.lights[0])
        assertEquals(s1, w.shapes[0])
        assertEquals(s2, w.shapes[1])
    }

    @Test
    fun `Intersect a world with a ray`() {
        val w = defaultWorld()
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))

        val intersections = w.intersect(r)

        assertEquals(4, intersections.size)
        assertEquals(4.0, intersections[0].t)
        assertEquals(4.5, intersections[1].t)
        assertEquals(5.5, intersections[2].t)
        assertEquals(6.0, intersections[3].t)
    }

    @Test
    fun `Shading an intersection`() {
        val w = defaultWorld()
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val shape = w.shapes[0]

        val i = Intersection(4.0, shape)
        val comps = prepareComputations(i, r)
        val c = w.shadeHit(comps)

        assertEquals(Colour(0.38066, 0.47583, 0.2855), c)
    }

    @Test
    fun `Shading an intersection from the inside`() {
        val w = defaultWorld()
        w.lights[0] = PointLight(point(0.0, 0.25, 0.0), WHITE)

        val r = Ray(point(0.0, 0.0, 0.0), vector(0.0, 0.0, 1.0))
        val shape = w.shapes[1]

        val i = Intersection(0.5, shape)
        val comps = prepareComputations(i, r)
        val c = w.shadeHit(comps)

        assertEquals(Colour(0.90498, 0.90498, 0.90498), c)
    }

    @Test
    fun `The colour when a ray misses`() {
        val w = defaultWorld()
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 1.0, 0.0))
        val c = w.colourAt(r)

        assertEquals(BLACK, c)
    }

    @Test
    fun `The colour when a ray hits`() {
        val w = defaultWorld()
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val c = w.colourAt(r)

        assertEquals(Colour(0.38066, 0.47583, 0.2855), c)
    }

    @Test
    fun `The colour with an intersection behind the ray`() {
        val w = defaultWorld()
        val outer = w.shapes[0]
        outer.material = Material(ambient = 1.0)

        val inner = w.shapes[1]
        inner.material = Material(ambient = 1.0)

        val r = Ray(point(0.0, 0.0, 0.75), vector(0.0, 0.0, -1.0))
        val c = w.colourAt(r)

        assertEquals(inner.material.colour, c)
    }
}