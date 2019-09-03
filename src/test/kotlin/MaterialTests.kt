package com.bhana

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MaterialTests {

    private var material = Material()
    private var position = ORIGIN

    @BeforeEach
    fun before() {
        material = Material()
        position = ORIGIN
    }

    @Test
    fun `The default material`() {
        assertEquals(Colour(1.0, 1.0, 1.0), material.colour)
        assertEquals(0.1, material.ambient)
        assertEquals(0.9, material.diffuse)
        assertEquals(0.9, material.specular)
        assertEquals(200.0, material.shininess)
    }

    @Test
    fun `Lighting with a surface in shadow`() {
        val eyeV = vector(0.0, 0.0, -1.0)
        val normalV = vector(0.0, 0.0, -1.0)
        val light = PointLight(point(0.0, 0.0, -10.0), WHITE)
        val inShadow = true

        val result = light.lighting(material, position, eyeV, normalV, inShadow)

        assertEquals(Colour(0.1, 0.1, 0.1), result)
    }
}