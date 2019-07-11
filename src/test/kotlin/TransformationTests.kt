package com.bhana

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

class TransformationTests {

    @Test
    fun `Multiplying by a translation matrix`() {
        val t = translation(5.0, -3.0, 2.0)
        val p1 = point(-3.0, 4.0, 5.0)
        val p2 = t * p1
        assertEquals(point(2.0, 1.0, 7.0), p2)
    }

    @Test
    fun `Multiplying by the inverse of a translation matrix`() {
        val t = translation(5.0, -3.0, 2.0)
        val inv = t.inverse()
        val p1 = point(-3.0, 4.0, 5.0)
        val p2 = inv * p1
        assertEquals(point(-8.0, 7.0, 3.0), p2)
    }

    @Test
    fun `Translation does not affect vectors`() {
        val t = translation(5.0, -3.0, 2.0)
        val v1 = vector(-3.0, 4.0, 5.0)
        val v2 = t * v1
        assertEquals(v1, v2)
    }

    @Test
    fun `Scaling a matrix applied to a point`() {
        val t = scaling(2.0, 3.0, 4.0)
        val p1 = point(-4.0, 6.0, 8.0)
        val p2 = t * p1
        assertEquals(point(-8.0, 18.0, 32.0), p2)
    }

    @Test
    fun `Scaling a matrix applied to a vector`() {
        val t = scaling(2.0, 3.0, 4.0)
        val v1 = vector(-4.0, 6.0, 8.0)
        val v2 = t * v1
        assertEquals(vector(-8.0, 18.0, 32.0), v2)
    }

    @Test
    fun `Multiplying by the inverse of a scaling matrix`() {
        val t = scaling(2.0, 3.0, 4.0).inverse()
        val v1 = vector(-4.0, 6.0, 8.0)
        val v2 = t * v1
        assertEquals(vector(-2.0, 2.0, 2.0), v2)
    }

    @Test
    fun `Reflection is scaling by a negative value`() {
        val t = scaling(-1.0, 1.0, 1.0)
        val p1 = point(2.0, 3.0, 4.0)
        val p2 = t * p1
        assertEquals(point(-2.0, 3.0, 4.0), p2)
    }

    @Test
    fun `Rotating a point around the x axis`() {
        val p1 = point(0.0, 1.0, 0.0)
        val halfQuarter = rotationX(radians(45.0))
        val fullQuarter = rotationX(radians(90.0))
        val p2 = halfQuarter * p1
        val p3 = fullQuarter * p1
        assertEquals(point(0.0, sqrt(2.0) / 2.0, sqrt(2.0) / 2.0), p2)
        assertEquals(point(0.0, 0.0, 1.0), p3)
    }
}