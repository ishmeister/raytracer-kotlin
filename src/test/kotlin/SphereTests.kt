package com.bhana

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

class SphereTests {

    @Test
    fun `A sphere's default transformation`() {
        assertEquals(identity(), Sphere("s").transform)
    }

    @Test
    fun `Changing a sphere's transformation`() {
        val s = Sphere("s")
        val t = translation(2.0, 3.0, 4.0)

        s.transform = t

        assertEquals(t, s.transform)
    }

    @Test
    fun `Intersecting a scaled sphere with a ray`() {
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val s = Sphere("s")

        s.transform = scaling(2.0, 2.0, 2.0)

        val xs = s.intersect(r)

        assertEquals(2, xs.size)
        assertEquals(3.0, xs[0].t)
        assertEquals(7.0, xs[1].t)
    }

    @Test
    fun `The normal on a sphere at a point on the x axis`() {
        val s = Sphere("s")
        val n = s.normalAt(point(1.0, 0.0, 0.0))
        assertEquals(vector(1.0, 0.0, 0.0), n)
    }

    @Test
    fun `The normal on a sphere at a point on the y axis`() {
        val s = Sphere("s")
        val n = s.normalAt(point(0.0, 1.0, 0.0))
        assertEquals(vector(0.0, 1.0, 0.0), n)
    }

    @Test
    fun `The normal on a sphere at a point on the z axis`() {
        val s = Sphere("s")
        val n = s.normalAt(point(0.0, 0.0, 1.0))
        assertEquals(vector(0.0, 0.0, 1.0), n)
    }

    @Test
    fun `The normal on a sphere at a non-axial point`() {
        val s = Sphere("s")
        val n = s.normalAt(point(sqrt(3.0) / 3.0, sqrt(3.0) / 3.0, sqrt(3.0) / 3.0))
        assertEquals(n.normalise(), n)
    }

    @Test
    fun `The normal is a normalised vector`() {
        val s = Sphere("s")
        val n = s.normalAt(point(sqrt(3.0) / 3.0, sqrt(3.0) / 3.0, sqrt(3.0) / 3.0))
        assertEquals(n.normalise(), n)
    }

    @Test
    fun `Computing the normal on a translated sphere`() {
        val s = Sphere("s")
        s.transform = translation(0.0, 1.0, 0.0)
        val n = s.normalAt(point(0.0, 1.70711, -0.70711))
        assertEquals(vector(0.0, 0.70711, -0.70711), n)
    }

    @Test
    fun `Computing the normal on a transformed sphere`() {
        val s = Sphere("s")
        s.transform = scaling(1.0, 0.5, 1.0) * rotationZ(Math.PI / 5.0)
        val n = s.normalAt(point(0.0, sqrt(2.0) / 2.0, -(sqrt(2.0) / 2.0)))
        assertEquals(vector(0.0, 0.97014, -0.24254), n)
    }

    @Test
    fun `A sphere has a default material`() {
        val s = Sphere("s")
        assertEquals(Material(), s.material)
    }

    @Test
    fun `A sphere may be assigned a material`() {
        val s = Sphere("s")
        s.material = Material(ambient = 1.0)
        assertEquals(Material(ambient = 1.0), s.material)
    }
}