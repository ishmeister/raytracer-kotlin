package raytracer.kotlin

import kotlin.math.sqrt
import kotlin.test.*

class WorldTests {

    @Test
    fun `Creating a world`() {
        val w = World()
        assertTrue(w.shapes.isEmpty())
        assertTrue(w.lights.isEmpty())
    }

    @Test
    fun `The default world`() {
        val light = PointLight(point(-10.0, 10.0, -10.0), WHITE)
        val s1 = Sphere("sphere1")
        s1.material = Material(Colour(0.8, 1.0, 0.6), diffuse = 0.7, specular = 0.2)

        val s2 = Sphere("sphere2")
        s2.transform = scaling(0.5, 0.5, 0.5)

        val w = defaultWorld()

        assertEquals(light, w.lights[0])
        assertEquals(s1, w.shapes[0])
        assertEquals(s2, w.shapes[1])
    }

    @Test
    fun `Intersect a world with a ray`() {
        val w = defaultWorld()
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))

        val intersections = w.intersect(r)

        assertEquals(4, intersections.size)
        assertEquals(4.0, intersections[0].t)
        assertEquals(4.5, intersections[1].t)
        assertEquals(5.5, intersections[2].t)
        assertEquals(6.0, intersections[3].t)
    }

    @Test
    fun `Shading an intersection`() {
        val w = defaultWorld()
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val shape = w.shapes[0]

        val i = Intersection(4.0, shape)
        val comps = i.prepareComputations(r)
        val c = w.shadeHit(comps)

        assertEquals(Colour(0.38066, 0.47583, 0.2855), c)
    }

    @Test
    fun `Shading an intersection from the inside`() {
        val w = defaultWorld()
        w.lights[0] = PointLight(point(0.0, 0.25, 0.0), WHITE)

        val r = Ray(point(0.0, 0.0, 0.0), vector(0.0, 0.0, 1.0))
        val shape = w.shapes[1]

        val i = Intersection(0.5, shape)
        val comps = i.prepareComputations(r)
        val c = w.shadeHit(comps)

        assertEquals(Colour(0.90498, 0.90498, 0.90498), c)
    }

    @Test
    fun `The colour when a ray misses`() {
        val w = defaultWorld()
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 1.0, 0.0))
        val c = w.colourAt(r)

        assertEquals(BLACK, c)
    }

    @Test
    fun `The colour when a ray hits`() {
        val w = defaultWorld()
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val c = w.colourAt(r)

        assertEquals(Colour(0.38066, 0.47583, 0.2855), c)
    }

    @Test
    fun `The colour with an intersection behind the ray`() {
        val w = defaultWorld()
        val outer = w.shapes[0]
        outer.material = Material(ambient = 1.0)

        val inner = w.shapes[1]
        inner.material = Material(ambient = 1.0)

        val r = Ray(point(0.0, 0.0, 0.75), vector(0.0, 0.0, -1.0))
        val c = w.colourAt(r)

        assertEquals(inner.material.colour, c)
    }

    @Test
    fun `There is no shadow when nothing is collinear with point and light`() {
        val w = defaultWorld()
        val p = point(0.0, 10.0, 0.0)
        val shadowed = w.isShadowed(p, w.lights[0])
        assertFalse(shadowed)
    }

    @Test
    fun `There is shadow when an object is between the point and the light`() {
        val w = defaultWorld()
        val p = point(10.0, -10.0, 10.0)
        val shadowed = w.isShadowed(p, w.lights[0])

        assertTrue(shadowed)
    }

    @Test
    fun `There is no shadow when an object is behind the light`() {
        val w = defaultWorld()
        val p = point(-20.0, 20.0, -20.0)
        val shadowed = w.isShadowed(p, w.lights[0])

        assertFalse(shadowed)
    }

    @Test
    fun `There is no shadow when an object is behind the point`() {
        val w = defaultWorld()
        val p = point(-2.0, 2.0, -2.0)
        val shadowed = w.isShadowed(p, w.lights[0])

        assertFalse(shadowed)
    }

    @Test
    fun `shadeHit is given an intersection in shadow`() {
        val w = World()
        w.lights.add(PointLight(point(0.0, 0.0, -10.0), WHITE))

        val s1 = Sphere("s1")
        w.shapes.add(s1)

        val s2 = Sphere("s2")
        s2.transform = translation(0.0, 0.0, 10.0)
        w.shapes.add(s2)

        val r = Ray(point(0.0, 0.0, 5.0), vector(0.0, 0.0, 1.0))
        val i = Intersection(4.0, s2)

        val comps = i.prepareComputations(r)
        val c = w.shadeHit(comps)

        assertEquals(Colour(0.1, 0.1, 0.1), c)
    }

    @Test
    fun `The reflective colour for a non-reflective material`() {
        val w = defaultWorld()
        val r = Ray(point(0.0, 0.0, 0.0), vector(0.0, 0.0, 1.0))
        val shape = w.shapes[1]
        shape.material = shape.material.copy(ambient = 1.0)
        val i = Intersection(1.0, shape)

        val comps = i.prepareComputations(r)
        val colour = w.reflectedColour(comps)

        assertEquals(BLACK, colour)
    }

    @Test
    fun `The reflected colour for a reflective material`() {
        val w = defaultWorld()
        val shape = Plane("p1")
        shape.material = Material(reflectivity = 0.5)
        shape.transform = translation(0.0, -1.0, 0.0)
        w.shapes.add(shape)

        val r = Ray(point(0.0, 0.0, -3.0), vector(0.0, -sqrt(2.0) / 2.0, sqrt(2.0) / 2.0))
        val i = Intersection(sqrt(2.0), shape)
        val comps = i.prepareComputations(r)

        val colour = w.reflectedColour(comps)

        assertEquals(Colour(0.19033, 0.23791, 0.14274), colour)
    }

    @Test
    fun `shade_hit() with a reflective material`() {
        val w = defaultWorld()
        val shape = Plane("p1")
        shape.material = Material(reflectivity = 0.5)
        shape.transform = translation(0.0, -1.0, 0.0)
        w.shapes.add(shape)

        val r = Ray(point(0.0, 0.0, -3.0), vector(0.0, -sqrt(2.0) / 2.0, sqrt(2.0) / 2.0))
        val i = Intersection(sqrt(2.0), shape)
        val comps = i.prepareComputations(r)

        val colour = w.shadeHit(comps)

        assertEquals(Colour(0.87675, 0.92434, 0.82917), colour)
    }

    @Test
    fun `colour_at() with mutually reflective surfaces`() {
        val w = World()
        w.lights.add(PointLight(point(0.0, 0.0, 0.0), WHITE))

        val lower = Plane("lower")
        lower.material = Material(reflectivity = 1.0)
        lower.transform = translation(0.0, -1.0, 0.0)
        w.shapes.add(lower)

        val upper = Plane("upper")
        upper.material = Material(reflectivity = 1.0)
        upper.transform = translation(0.0, 1.0, 0.0)
        w.shapes.add(upper)

        val r = Ray(point(0.0, 0.0, 0.0), vector(0.0, 1.0, 0.0))

        val colour = w.colourAt(r)

        assertNotNull(colour)
    }

    @Test
    fun `The reflected colour at the maximum recursive depth`() {
        val w = defaultWorld()
        val shape = Plane("p1")
        shape.material = Material(reflectivity = 0.5)
        shape.transform = translation(0.0, -1.0, 0.0)
        w.shapes.add(shape)

        val r = Ray(point(0.0, 0.0, -3.0), vector(0.0, -sqrt(2.0) / 2.0, sqrt(2.0) / 2.0))
        val i = Intersection(sqrt(2.0), shape)
        val comps = i.prepareComputations(r)

        val colour = w.reflectedColour(comps, 0)

        assertEquals(BLACK, colour)
    }

    @Test
    fun `The refracted colour with an opaque surface`() {
        val w = defaultWorld()
        val shape = w.shapes[0]
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val xs = listOf(Intersection(4.0, shape), Intersection(6.0, shape))
        val comps = xs[0].prepareComputations(r, xs)

        val c = w.refractedColour(comps, 5)

        assertEquals(BLACK, c)
    }

    @Test
    fun `The refracted colour at the maximum recursion depth`() {
        val w = defaultWorld()
        w.shapes[0].material = Material(transparency = 1.0, refractiveIndex = 1.5)

        val shape = w.shapes[0]
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val xs = listOf(Intersection(4.0, shape), Intersection(6.0, shape))
        val comps = xs[0].prepareComputations(r, xs)

        val c = w.refractedColour(comps, 0)

        assertEquals(BLACK, c)
    }

    @Test
    fun `The refracted colour under total internal reflection`() {
        val w = defaultWorld()
        w.shapes[0].material = Material(transparency = 1.0, refractiveIndex = 1.5)

        val shape = w.shapes[0]
        val r = Ray(point(0.0, 0.0, sqrt(2.0) / 2.0), vector(0.0, 1.0, 0.0))
        val xs = listOf(Intersection(-sqrt(2.0) / 2.0, shape), Intersection(sqrt(2.0) / 2.0, shape))
        val comps = xs[1].prepareComputations(r, xs)

        val c = w.refractedColour(comps, 5)

        assertEquals(BLACK, c)
    }

    @Test
    fun `The refracted colour with a refracted ray`() {
        val w = defaultWorld()
        w.shapes[0].material = Material(ambient = 1.0, pattern = TestPattern())
        w.shapes[1].material = Material(transparency = 1.0, refractiveIndex = 1.5)

        val a = w.shapes[0]
        val b = w.shapes[1]

        val r = Ray(point(0.0, 0.0, 0.1), vector(0.0, 1.0, 0.0))
        val xs =
            listOf(Intersection(-0.9899, a), Intersection(-0.4899, b), Intersection(0.4899, b), Intersection(0.9899, a))
        val comps = xs[2].prepareComputations(r, xs)

        val c = w.refractedColour(comps, 5)

        assertEquals(Colour(0.0, 0.99887, 0.04721), c)
    }

    @Test
    fun `shade_hit() with a transparent material`() {
        val w = defaultWorld()

        val floor = Plane("floor")
        floor.transform = translation(0.0, -1.0, 0.0)
        floor.material = Material(transparency = 0.5, refractiveIndex = 1.5)
        w.shapes.add(floor)

        val ball = Sphere("ball")
        ball.transform = translation(0.0, -3.5, -0.5)
        ball.material = Material(colour = Colour(1.0, 0.0, 0.0), ambient = 0.5)
        w.shapes.add(ball)

        val r = Ray(point(0.0, 0.0, -3.0), vector(0.0, -sqrt(2.0) / 2.0, sqrt(2.0) / 2.0))
        val xs = listOf(Intersection(sqrt(2.0), floor))
        val comps = xs[0].prepareComputations(r, xs)

        val colour = w.shadeHit(comps, 5)

        assertEquals(Colour(0.93642, 0.68642, 0.68642), colour)
    }

    @Test
    fun `shade_hit() with a reflective, transparent material`() {
        val w = defaultWorld()
        val r = Ray(point(0.0, 0.0, -3.0), vector(0.0, -sqrt(2.0) / 2.0, sqrt(2.0) / 2.0))

        val floor = Plane("floor")
        floor.transform = translation(0.0, -1.0, 0.0)
        floor.material = Material(reflectivity = 0.5, transparency = 0.5, refractiveIndex = 1.5)
        w.shapes.add(floor)

        val ball = Sphere("ball")
        ball.transform = translation(0.0, -3.5, -0.5)
        ball.material = Material(colour = RED, ambient = 0.5)
        w.shapes.add(ball)

        val xs = listOf(Intersection(sqrt(2.0), floor))
        val comps = xs[0].prepareComputations(r, xs)

        val colour = w.shadeHit(comps, 5)

        assertEquals(Colour(0.93391, 0.69643, 0.69243), colour)
    }
}