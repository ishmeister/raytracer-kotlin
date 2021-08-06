package com.kotrt

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

class TupleTests {

    @Test
    fun `Tuple equality`() {
        val p1 = point(1.1 + 2.2, -0.0, 3.0)
        val p2 = point(3.3, 0.0, 3.0)

        assertEquals(p1, p2)
        assertTrue(p1 == p2)
        assertFalse(p1 === p2)
    }

    @Test
    fun `Point() creates a tuple with w=1`() {
        val p = point(4.0, -4.0, 3.0)

        assertEquals(4.0, p.x)
        assertEquals(-4.0, p.y)
        assertEquals(3.0, p.z)
        assertEquals(p.w, 1.0)
        assertTrue(p.isPoint())
        assertFalse(p.isVector())
    }

    @Test
    fun `Vector() creates a tuple with w=0`() {
        val p = vector(4.0, -4.0, 3.0)

        assertEquals(4.0, p.x)
        assertEquals(-4.0, p.y)
        assertEquals(3.0, p.z)
        assertEquals(p.w, 0.0)
        assertTrue(p.isVector())
        assertFalse(p.isPoint())
    }

    @Test
    fun `Adding a point to a vector makes a point`() {
        val p1 = point(3.0, -2.0, 5.0)
        val v = vector(-2.0, 3.0, 1.0)
        val p2 = p1 + v

        assertEquals(point(1.0, 1.0, 6.0), p2)
        assertTrue(p2.isPoint())
    }

    @Test
    fun `Adding a vector to a vector makes a vector`() {
        val p = vector(3.0, -2.0, 5.0)
        val v1 = vector(-2.0, 3.0, 1.0)
        val v2 = p + v1

        assertEquals(vector(1.0, 1.0, 6.0), v2)
        assertTrue(v2.isVector())
    }

    @Test
    fun `Subtracting two points make a vector`() {
        val p1 = point(3.0, 2.0, 1.0)
        val p2 = point(5.0, 6.0, 7.0)
        val v = p1 - p2

        assertEquals(vector(-2.0, -4.0, -6.0), v)
        assertTrue(v.isVector())
    }

    @Test
    fun `Subtracting a vector from a point makes a point`() {
        val p1 = point(3.0, 2.0, 1.0)
        val v = vector(5.0, 6.0, 7.0)
        val p2 = p1 - v

        assertEquals(point(-2.0, -4.0, -6.0), p2)
        assertTrue(p2.isPoint())
    }

    @Test
    fun `Subtracting two vectors makes a vector`() {
        val v1 = vector(3.0, 2.0, 1.0)
        val v2 = vector(5.0, 6.0, 7.0)
        val v3 = v1 - v2

        assertEquals(vector(-2.0, -4.0, -6.0), v3)
        assertTrue(v3.isVector())
    }

    @Test
    fun `Subtracting a vector from the zero vector`() {
        val v1 = vector(0.0, 0.0, 0.0)
        val v2 = vector(1.0, -2.0, 3.0)
        val v3 = v1 - v2

        assertEquals(vector(-1.0, 2.0, -3.0), v3)
    }

    @Test
    fun `Negating a tuple`() {
        val t = Tuple(1.0, -2.0, 3.0, 4.0)
        val t2 = -t

        assertEquals(Tuple(-1.0, 2.0, -3.0, -4.0), t2)
    }

    @Test
    fun `Multiplying a tuple by a scalar`() {
        val t = Tuple(1.0, -2.0, 3.0, -4.0)
        val t2 = t * 3.5

        assertEquals(Tuple(3.5, -7.0, 10.5, -14.0), t2)
    }

    @Test
    fun `Multiplying a tuple by a fraction`() {
        val t = Tuple(1.0, -2.0, 3.0, -4.0)
        val t2 = t * 0.5

        assertEquals(Tuple(0.5, -1.0, 1.5, -2.0), t2)
    }

    @Test
    fun `Dividing a tuple by a scalar`() {
        val t = Tuple(1.0, -2.0, 3.0, -4.0)
        val t2 = t / 2.0

        assertEquals(Tuple(0.5, -1.0, 1.5, -2.0), t2)
    }

    @Test
    fun `Magnitude of Vector(1, 0, 0)`() {
        val v = vector(1.0, 0.0, 0.0)
        assertEquals(1.0, v.magnitude())
    }

    @Test
    fun `Magnitude of Vector(0, 1, 0)`() {
        val v = vector(0.0, 1.0, 0.0)
        assertEquals(1.0, v.magnitude())
    }

    @Test
    fun `Magnitude of Vector(0, 0, 1)`() {
        val v = vector(0.0, 0.0, 1.0)
        assertEquals(1.0, v.magnitude())
    }

    @Test
    fun `Magnitude of Vector(1, 2, 3)`() {
        val v = vector(1.0, 2.0, 3.0)
        assertEquals(sqrt(14.0), v.magnitude())
    }

    @Test
    fun `Magnitude of Vector(-1, -2, -3)`() {
        val v = vector(-1.0, -2.0, -3.0)
        assertEquals(sqrt(14.0), v.magnitude())
    }

    @Test
    fun `Normalising Vector(4, 0, 0) gives Vector(1, 0, 0)`() {
        val v = vector(4.0, 0.0, 0.0)
        assertEquals(vector(1.0, 0.0, 0.0), v.normalise())
    }

    @Test
    fun `Normalising Vector(1, 2, 3)`() {
        val v = vector(1.0, 2.0, 3.0)
        assertEquals(vector(0.26726, 0.53452, 0.80178), v.normalise())
    }

    @Test
    fun `Magnitude of a normalised vector is 1`() {
        val v = vector(1.0, 2.0, 3.0)
        assertEquals(1.0, v.normalise().magnitude())
    }

    @Test
    fun `Dot product of two vectors`() {
        val v1 = vector(1.0, 2.0, 3.0)
        val v2 = vector(2.0, 3.0, 4.0)

        assertEquals(20.0, v1.dot(v2))
    }

    @Test
    fun `Cross product of two vectors`() {
        val v1 = vector(1.0, 2.0, 3.0)
        val v2 = vector(2.0, 3.0, 4.0)

        assertEquals(vector(-1.0, 2.0, -1.0), v1.cross(v2))
        assertEquals(vector(1.0, -2.0, 1.0), v2.cross(v1))
    }

    @Test
    fun `Reflecting a vector approaching at 45 deg`() {
        val v = vector(1.0, -1.0, 0.0)
        val n = vector(0.0, 1.0, 0.0)
        val r = v.reflect(n)
        assertEquals(vector(1.0, 1.0, 0.0), r)
    }

    @Test
    fun `Reflecting a vector off a slanted surface`() {
        val v = vector(0.0, -1.0, 0.0)
        val n = vector(sqrt(2.0) / 2.0, sqrt(2.0) / 2.0, 0.0)
        val r = v.reflect(n)
        assertEquals(vector(1.0, 0.0, 0.0), r)
    }

}
