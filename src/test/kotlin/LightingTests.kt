package com.bhana

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

class LightingTests {

    @Test
    fun `A point light has a position and intensity`() {
        val intensity = Colour(1.0, 1.0, 1.0)
        val position = point(0.0, 0.0, 0.0)
        val light = PointLight(position, intensity)

        assertEquals(position, light.position)
        assertEquals(intensity, light.intensity)
    }

    @Test
    fun `Lighting with the eye between light and surface`() {
        val material = Material()
        val position = point(0.0, 0.0, 0.0)

        val eyeVec = vector(0.0, 0.0, -1.0)
        val normalVec = vector(0.0, 0.0, -1.0)
        val light = PointLight(point(0.0, 0.0, -10.0), Colour(1.0, 1.0, 1.0))
        val result = lighting(material, light, position, eyeVec, normalVec)

        assertEquals(Colour(1.9, 1.9, 1.9), result)
    }

    @Test
    fun `Lighting with the eye between light and surface, eye offset 45 deg`() {
        val material = Material()
        val position = point(0.0, 0.0, 0.0)

        val eyeVec = vector(0.0, sqrt(2.0) / 2.0, -(sqrt(2.0) / 2.0))
        val normalVec = vector(0.0, 0.0, -1.0)
        val light = PointLight(point(0.0, 0.0, -10.0), Colour(1.0, 1.0, 1.0))
        val result = lighting(material, light, position, eyeVec, normalVec)

        assertEquals(Colour(1.0, 1.0, 1.0), result)
    }

    @Test
    fun `Lighting with the eye opposite surface, light offset 45 deg`() {
        val material = Material()
        val position = point(0.0, 0.0, 0.0)

        val eyeVec = vector(0.0, 0.0, -1.0)
        val normalVec = vector(0.0, 0.0, -1.0)
        val light = PointLight(point(0.0, 10.0, -10.0), Colour(1.0, 1.0, 1.0))
        val result = lighting(material, light, position, eyeVec, normalVec)

        assertEquals(Colour(0.7364, 0.7364, 0.7364), result)
    }

    @Test
    fun `Lighting with the eye in the path of the reflection vector`() {
        val material = Material()
        val position = point(0.0, 0.0, 0.0)

        val eyeVec = vector(0.0, -(sqrt(2.0) / 2.0), -(sqrt(2.0) / 2.0))
        val normalVec = vector(0.0, 0.0, -1.0)
        val light = PointLight(point(0.0, 10.0, -10.0), Colour(1.0, 1.0, 1.0))
        val result = lighting(material, light, position, eyeVec, normalVec)

        assertEquals(Colour(1.6364, 1.6364, 1.6364), result)
    }

    @Test
    fun `Lighting with the eye behind the surface`() {
        val material = Material()
        val position = point(0.0, 0.0, 0.0)

        val eyeVec = vector(0.0, 0.0, -1.0)
        val normalVec = vector(0.0, 0.0, -1.0)
        val light = PointLight(point(0.0, 0.0, 10.0), Colour(1.0, 1.0, 1.0))
        val result = lighting(material, light, position, eyeVec, normalVec)

        assertEquals(Colour(0.1, 0.1, 0.1), result)
    }
}