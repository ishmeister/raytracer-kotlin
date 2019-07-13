package com.bhana

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RayTests {

    @Test
    fun `Creating and querying a ray`() {
        val origin = point(1.0, 2.0, 3.0)
        val direction = vector(4.0, 5.0, 6.0)
        val r = Ray(origin, direction)

        assertEquals(r.origin, origin)
        assertEquals(r.direction, direction)
    }

    @Test
    fun `Computing a point from a distance`() {
        val r = Ray(point(2.0, 3.0, 4.0), vector(1.0, 0.0, 0.0))
        
        assertEquals(point(2.0, 3.0, 4.0), r.position(0.0))
        assertEquals(point(3.0, 3.0, 4.0), r.position(1.0))
        assertEquals(point(1.0, 3.0, 4.0), r.position(-1.0))
        assertEquals(point(4.5, 3.0, 4.0), r.position(2.5))
    }

    @Test
    fun `A ray intersects a sphere at two points`() {
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val s = Sphere()

        val list = s.intersect(r)

        assertEquals(2, list.size)
        assertEquals(4.0, list[0])
        assertEquals(6.0, list[1])
    }

    @Test
    fun `A ray intersects a sphere at a tangent`() {
        val r = Ray(point(0.0, 1.0, -5.0), vector(0.0, 0.0, 1.0))
        val s = Sphere()

        val list = s.intersect(r)

        assertEquals(2, list.size)
        assertEquals(5.0, list[0])
        assertEquals(5.0, list[1])
    }

    @Test
    fun `A ray misses a sphere`() {
        val r = Ray(point(0.0, 2.0, -5.0), vector(0.0, 0.0, 1.0))
        val s = Sphere()

        val list = s.intersect(r)

        assertEquals(0, list.size)
    }

    @Test
    fun `A ray originates inside a sphere`() {
        val r = Ray(point(0.0, 0.0, 0.0), vector(0.0, 0.0, 1.0))
        val s = Sphere()

        val list = s.intersect(r)

        assertEquals(2, list.size)
        assertEquals(-1.0, list[0])
        assertEquals(1.0, list[1])
    }

    @Test
    fun `A sphere is behind a ray`() {
        val r = Ray(point(0.0, 0.0, 5.0), vector(0.0, 0.0, 1.0))
        val s = Sphere()

        val list = s.intersect(r)

        assertEquals(2, list.size)
        assertEquals(-6.0, list[0])
        assertEquals(-4.0, list[1])
    }
}