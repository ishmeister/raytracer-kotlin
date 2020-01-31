package com.bhana

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MaterialTests {

    private val shape = Sphere("s1")

    @Test
    fun `The default material`() {
        val material = Material()

        assertEquals(Colour(1.0, 1.0, 1.0), material.colour)
        assertEquals(0.1, material.ambient)
        assertEquals(0.9, material.diffuse)
        assertEquals(0.9, material.specular)
        assertEquals(200.0, material.shininess)
    }

    @Test
    fun `Lighting with a surface in shadow`() {
        val material = Material()

        val eyeV = vector(0.0, 0.0, -1.0)
        val normalV = vector(0.0, 0.0, -1.0)
        val light = PointLight(point(0.0, 0.0, -10.0), WHITE)
        val inShadow = true

        val result = light.lighting(material, shape, ORIGIN, eyeV, normalV, inShadow)

        assertEquals(Colour(0.1, 0.1, 0.1), result)
    }

    @Test
    fun `Lighting with a pattern applied`() {
        val material = Material(ambient = 1.0, diffuse = 0.0, specular = 0.0, pattern = StripePattern(WHITE, BLACK))

        val eyeV = vector(0.0, 0.0, -1.0)
        val normalV = vector(0.0, 0.0, -1.0)
        val light = PointLight(point(0.0, 0.0, -10.0), WHITE)
        val inShadow = false

        val c1 = light.lighting(material, shape, point(0.9, 0.0, 0.0), eyeV, normalV, inShadow)
        val c2 = light.lighting(material, shape, point(1.1, 0.0, 0.0), eyeV, normalV, inShadow)

        assertEquals(WHITE, c1)
        assertEquals(BLACK, c2)
    }

    @Test
    fun `Reflectivity for the default material`() {
        val material = Material()
        assertEquals(0.0, material.reflectivity)
    }
}