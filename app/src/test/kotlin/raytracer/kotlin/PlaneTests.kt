package raytracer.kotlin

import kotlin.test.Test
import kotlin.test.assertEquals

class PlaneTests {

    @Test
    fun `The normal of a plane is constant everywhere`() {
        val p = Plane("p1")

        val n1 = p.normalAtLocal(point(0.0, 0.0, 0.0))
        val n2 = p.normalAtLocal(point(10.0, 0.0, -10.0))
        val n3 = p.normalAtLocal(point(-5.0, 0.0, 150.0))

        assertEquals(vector(0.0, 1.0, 0.0), n1)
        assertEquals(vector(0.0, 1.0, 0.0), n2)
        assertEquals(vector(0.0, 1.0, 0.0), n3)
    }

    @Test
    fun `Intersect with a ray parallel to the plane`() {
        val p = Plane("p1")
        val r = Ray(point(0.0, 10.0, 0.0), vector(0.0, 0.0, 1.0))

        val xs = p.intersectLocal(r)

        assertEquals(0, xs.size)
    }

    @Test
    fun `Intersect with a coplanar ray`() {
        val p = Plane("p1")
        val r = Ray(point(0.0, 0.0, 0.0), vector(0.0, 0.0, 1.0))

        val xs = p.intersectLocal(r)

        assertEquals(0, xs.size)
    }

    @Test
    fun `A ray intersecting the plane from above`() {
        val p = Plane("p1")
        val r = Ray(point(0.0, 1.0, 0.0), vector(0.0, -1.0, 0.0))

        val xs = p.intersectLocal(r)

        assertEquals(1, xs.size)
        assertEquals(1.0, xs[0].t)
        assertEquals(p, xs[0].shape)
    }

    @Test
    fun `A ray intersecting the plane from below`() {
        val p = Plane("p1")
        val r = Ray(point(0.0, -1.0, 0.0), vector(0.0, 1.0, 0.0))

        val xs = p.intersectLocal(r)

        assertEquals(1, xs.size)
        assertEquals(1.0, xs[0].t)
        assertEquals(p, xs[0].shape)
    }
}