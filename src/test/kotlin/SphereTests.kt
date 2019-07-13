package com.bhana

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

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
}