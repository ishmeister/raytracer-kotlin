package raytracer.kotlin

import kotlin.math.sqrt
import kotlin.test.Test
import kotlin.test.assertEquals

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
    fun `Intersecting a sphere with a ray`() {
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val s = Sphere("s")

        val xs = s.intersectLocal(r)

        assertEquals(2, xs.size)
        assertEquals(4.0, xs[0].t)
        assertEquals(6.0, xs[1].t)
    }

    @Test
    fun `The normal on a sphere at a point on the x axis`() {
        val s = Sphere("s")
        val n = s.normalAtLocal(point(1.0, 0.0, 0.0))

        assertEquals(vector(1.0, 0.0, 0.0), n)
    }

    @Test
    fun `The normal on a sphere at a point on the y axis`() {
        val s = Sphere("s")
        val n = s.normalAtLocal(point(0.0, 1.0, 0.0))

        assertEquals(vector(0.0, 1.0, 0.0), n)
    }

    @Test
    fun `The normal on a sphere at a point on the z axis`() {
        val s = Sphere("s")
        val n = s.normalAtLocal(point(0.0, 0.0, 1.0))

        assertEquals(vector(0.0, 0.0, 1.0), n)
    }

    @Test
    fun `The normal on a sphere at a non-axial point`() {
        val s = Sphere("s")
        val n = s.normalAtLocal(point(sqrt(3.0) / 3.0, sqrt(3.0) / 3.0, sqrt(3.0) / 3.0))

        assertEquals(vector(sqrt(3.0) / 3.0, sqrt(3.0) / 3.0, sqrt(3.0) / 3.0), n)
    }

    @Test
    fun `The normal is a normalised vector`() {
        val s = Sphere("s")
        val n = s.normalAtLocal(point(sqrt(3.0) / 3.0, sqrt(3.0) / 3.0, sqrt(3.0) / 3.0))

        assertEquals(n.normalise(), n)
    }

    @Test
    fun `A sphere has a default material`() {
        assertEquals(Material(), Sphere("s").material)
    }

    @Test
    fun `A sphere may be assigned a material`() {
        val s = Sphere("s")
        s.material = Material(ambient = 1.0)

        assertEquals(Material(ambient = 1.0), s.material)
    }
}