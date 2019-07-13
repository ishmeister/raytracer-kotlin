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
}
