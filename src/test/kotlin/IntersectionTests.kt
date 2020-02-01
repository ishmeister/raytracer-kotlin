package com.bhana

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.math.sqrt

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

        val i = findHit(arrayListOf(i1, i2))

        assertEquals(i1, i)
    }

    @Test
    fun `The hit, when some intersections have negative t`() {
        val s = Sphere("s")
        val i1 = Intersection(-1.0, s)
        val i2 = Intersection(1.0, s)

        val i = findHit(arrayListOf(i1, i2))

        assertEquals(i2, i)
    }

    @Test
    fun `The hit, when all intersections have negative t`() {
        val s = Sphere("s")
        val i1 = Intersection(-2.0, s)
        val i2 = Intersection(-1.0, s)

        val i = findHit(arrayListOf(i1, i2))

        assertNull(i)
    }

    @Test
    fun `The hit is always the lowest non-negative intersection`() {
        val s = Sphere("s")
        val i1 = Intersection(5.0, s)
        val i2 = Intersection(7.0, s)
        val i3 = Intersection(-3.0, s)
        val i4 = Intersection(2.0, s)

        val i = findHit(arrayListOf(i1, i2, i3, i4))

        assertEquals(i4, i)
    }

    @Test
    fun `Pre-computing the state of an intersection`() {
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val shape = Sphere("s1")
        val i = Intersection(4.0, shape)
        val comps = i.prepareComputations(r)

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
        val comps = i.prepareComputations(r)

        assertFalse(comps.inside)
    }

    @Test
    fun `The hit, when an intersection occurs on the inside`() {
        val r = Ray(point(0.0, 0.0, 0.0), vector(0.0, 0.0, 1.0))
        val shape = Sphere("s1")
        val i = Intersection(1.0, shape)
        val comps = i.prepareComputations(r)

        assertTrue(comps.inside)
        assertEquals(i.t, comps.t)
        assertEquals(shape, comps.shape)
        assertEquals(point(0.0, 0.0, 1.0), comps.point)
        assertEquals(vector(0.0, 0.0, -1.0), comps.eyeVec)
        assertEquals(vector(0.0, 0.0, -1.0), comps.normalVec)
    }

    @Test
    fun `The hit should offset the point`() {
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val shape = Sphere("s")
        shape.transform = translation(0.0, 0.0, 1.0)

        val i = Intersection(5.0, shape)
        val comps = i.prepareComputations(r)

        assertTrue(comps.overPoint.z < -EPSILON / 2.0)
        assertTrue(comps.point.z > comps.overPoint.z)
    }

    @Test
    fun `Pre-computing the reflection vector`() {
        val shape = Plane("p1")
        val r = Ray(point(0.0, 1.0, -1.0), vector(0.0, -sqrt(2.0) / 2.0, sqrt(2.0) / 2.0))
        val i = Intersection(sqrt(2.0), shape)
        val comps = i.prepareComputations(r)

        assertEquals(vector(0.0, sqrt(2.0) / 2.0, sqrt(2.0) / 2.0), comps.reflectVec)
    }

    @ParameterizedTest
    @CsvSource(
        "0, 1.0, 1.5",
        "1, 1.5, 2.0",
        "2, 2.0, 2.5",
        "3, 2.5, 2.5",
        "4, 2.5, 1.5",
        "5, 1.5, 1.0"
    )
    fun `Finding n1 and n2 at various intersections`(index: Int, n1: Double, n2: Double) {
        val a = glassSphere("a")
        a.transform = scaling(2.0, 2.0, 2.0)
        a.material = a.material.copy(refractiveIndex = 1.5)

        val b = glassSphere("b")
        b.transform = translation(0.0, 0.0, -0.25)
        b.material = b.material.copy(refractiveIndex = 2.0)

        val c = glassSphere("c")
        c.transform = translation(0.0, 0.0, 0.25)
        c.material = c.material.copy(refractiveIndex = 2.5)

        val r = Ray(point(0.0, 0.0, -4.0), vector(0.0, 0.0, 1.0))
        val xs = listOf(
            Intersection(2.0, a),
            Intersection(2.75, b),
            Intersection(3.25, c),
            Intersection(4.75, b),
            Intersection(5.25, c),
            Intersection(6.0, a)
        )
        val comps = xs[index].prepareComputations(r, xs)

        assertEquals(n1, comps.n1, "n1")
        assertEquals(n2, comps.n2, "n2")
    }
}
