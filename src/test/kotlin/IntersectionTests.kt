package com.bhana

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class IntersectionTests {

    @Test
    fun `A ray intersects a sphere at two points`() {
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val s = Sphere("s")

        val list = s.intersect(r)

        assertEquals(2, list.size)
        assertEquals(4.0, list[0].t)
        assertEquals(6.0, list[1].t)
    }

    @Test
    fun `A ray intersects a sphere at a tangent`() {
        val r = Ray(point(0.0, 1.0, -5.0), vector(0.0, 0.0, 1.0))
        val s = Sphere("s")

        val list = s.intersect(r)

        assertEquals(2, list.size)
        assertEquals(5.0, list[0].t)
        assertEquals(5.0, list[1].t)
    }

    @Test
    fun `A ray misses a sphere`() {
        val r = Ray(point(0.0, 2.0, -5.0), vector(0.0, 0.0, 1.0))
        val s = Sphere("s")

        val list = s.intersect(r)

        assertEquals(0, list.size)
    }

    @Test
    fun `A ray originates inside a sphere`() {
        val r = Ray(point(0.0, 0.0, 0.0), vector(0.0, 0.0, 1.0))
        val s = Sphere("s")

        val list = s.intersect(r)

        assertEquals(2, list.size)
        assertEquals(-1.0, list[0].t)
        assertEquals(1.0, list[1].t)
    }

    @Test
    fun `A sphere is behind a ray`() {
        val r = Ray(point(0.0, 0.0, 5.0), vector(0.0, 0.0, 1.0))
        val s = Sphere("s")

        val list = s.intersect(r)

        assertEquals(2, list.size)
        assertEquals(-6.0, list[0].t)
        assertEquals(-4.0, list[1].t)
    }

    @Test
    fun `An intersection encapsulates t and object`() {
        val s = Sphere("s")
        val i = Intersection(3.5, s)

        assertEquals(3.5, i.t)
        assertEquals(s, i.shape)
    }

    @Test
    fun `Intersect sets the object on the intersection`() {
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val s = Sphere("s")

        val list = s.intersect(r)

        assertEquals(2, list.size)
        assertEquals(s, list[0].shape)
        assertEquals(s, list[1].shape)
    }

    @Test
    fun `The hit, when all intersections have positive t`() {
        val s = Sphere("s")
        val i1 = Intersection(1.0, s)
        val i2 = Intersection(2.0, s)

        val i = hit(arrayListOf(i1, i2))

        assertEquals(i1, i)
    }

    @Test
    fun `The hit, when some intersections have negative t`() {
        val s = Sphere("s")
        val i1 = Intersection(-1.0, s)
        val i2 = Intersection(1.0, s)

        val i = hit(arrayListOf(i1, i2))

        assertEquals(i2, i)
    }

    @Test
    fun `The hit, when all intersections have negative t`() {
        val s = Sphere("s")
        val i1 = Intersection(-2.0, s)
        val i2 = Intersection(-1.0, s)

        val i = hit(arrayListOf(i1, i2))

        assertNull(i)
    }

    @Test
    fun `The hit is always the lowest non-negative intersection`() {
        val s = Sphere("s")
        val i1 = Intersection(5.0, s)
        val i2 = Intersection(7.0, s)
        val i3 = Intersection(-3.0, s)
        val i4 = Intersection(2.0, s)

        val i = hit(arrayListOf(i1, i2, i3, i4))

        assertEquals(i4, i)
    }

    @Test
    fun `Pre-computing the state of an intersection`() {
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val shape = Sphere("s1")
        val i = Intersection(4.0, shape)
        val comps = prepareComputations(i, r)

        assertEquals(i.t, comps.t)
        assertEquals(shape, comps.shape)
        assertEquals(point(0.0, 0.0, -1.0), comps.point)
        assertEquals(vector(0.0, 0.0, -1.0), comps.eyeVec)
        assertEquals(vector(0.0, 0.0, -1.0), comps.normalVec)
    }

    @Test
    fun `The hit, when an intersection occurs on the outside`() {
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val shape = Sphere("s1")
        val i = Intersection(4.0, shape)
        val comps = prepareComputations(i, r)

        assertFalse(comps.inside)
    }

    @Test
    fun `The hit, when an intersection occurs on the inside`() {
        val r = Ray(point(0.0, 0.0, 0.0), vector(0.0, 0.0, 1.0))
        val shape = Sphere("s1")
        val i = Intersection(1.0, shape)
        val comps = prepareComputations(i, r)

        assertTrue(comps.inside)
        assertEquals(i.t, comps.t)
        assertEquals(shape, comps.shape)
        assertEquals(point(0.0, 0.0, 1.0), comps.point)
        assertEquals(vector(0.0, 0.0, -1.0), comps.eyeVec)
        assertEquals(vector(0.0, 0.0, -1.0), comps.normalVec)
    }
}
