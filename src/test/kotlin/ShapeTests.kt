package com.bhana

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

class ShapeTests {

    @Test
    fun `The default transformation`() {
        val s = TestShape()
        assertEquals(identity(), s.transform)
    }

    @Test
    fun `Assigning a transformation`() {
        val s = TestShape()
        s.transform = translation(2.0, 3.0, 4.0)
        assertEquals(translation(2.0, 3.0, 4.0), s.transform)
    }

    @Test
    fun `The default material`() {
        val s = TestShape()
        assertEquals(Material(), s.material)
    }

    @Test
    fun `Assigning a material`() {
        val s = TestShape()
        val m = Material(ambient = 1.0)
        s.material = m
        assertEquals(m, s.material)
    }

    @Test
    fun `Intersecting a scaled shape with a ray`() {
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val s = TestShape()

        s.transform = scaling(2.0, 2.0, 2.0)
        s.intersect(r)

        assertEquals(point(0.0, 0.0, -2.50), s.savedRay?.origin)
        assertEquals(vector(0.0, 0.0, 0.5), s.savedRay?.direction)
    }

    @Test
    fun `Intersecting a translated shape with a ray`() {
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val s = TestShape()

        s.transform = translation(5.0, 0.0, 0.0)
        s.intersect(r)

        assertEquals(point(-5.0, 0.0, -5.0), s.savedRay?.origin)
        assertEquals(vector(0.0, 0.0, 1.0), s.savedRay?.direction)
    }

    @Test
    fun `Computing the normal on a translated shape`() {
        val s = TestShape()

        s.transform = translation(0.0, 1.0, 0.0)
        val n = s.normalAt(point(0.0, 1.70711, -0.70711))

        assertEquals(vector(0.0, 0.70711, -0.70711), n)
    }

    @Test
    fun `Computing the normal on a transformed shape`() {
        val s = TestShape()

        s.transform = scaling(1.0, 0.5, 1.0) * rotationZ(Math.PI / 5.0)
        val n = s.normalAt(point(0.0, sqrt(2.0) / 2.0, -sqrt(2.0) / 2.0))

        assertEquals(vector(0.0, 0.97014, -0.24254), n)
    }
}