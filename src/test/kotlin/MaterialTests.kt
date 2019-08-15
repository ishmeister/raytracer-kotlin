package com.bhana

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MaterialTests {

    @Test
    fun `The default material`() {
        val m = Material()
        assertEquals(Colour(1.0, 1.0, 1.0), m.colour)
        assertEquals(0.1, m.ambient)
        assertEquals(0.9, m.diffuse)
        assertEquals(0.9, m.specular)
        assertEquals(200.0, m.shininess)
    }

}