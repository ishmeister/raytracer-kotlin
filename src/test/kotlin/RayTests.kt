package com.kotrt

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
    fun `Translating a ray`() {
        val r = Ray(point(1.0, 2.0, 3.0), vector(0.0, 1.0, 0.0))
        val m = translation(3.0, 4.0, 5.0)

        val r2 = r.transform(m)

        assertEquals(point(4.0, 6.0, 8.0), r2.origin)
        assertEquals(vector(0.0, 1.0, 0.0), r2.direction)
    }

    @Test
    fun `Scaling a ray`() {
        val r = Ray(point(1.0, 2.0, 3.0), vector(0.0, 1.0, 0.0))
        val m = scaling(2.0, 3.0, 4.0)

        val r2 = r.transform(m)

        assertEquals(point(2.0, 6.0, 12.0), r2.origin)
        assertEquals(vector(0.0, 3.0, 0.0), r2.direction)
    }
}
