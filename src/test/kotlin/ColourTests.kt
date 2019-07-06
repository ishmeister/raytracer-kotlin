package com.bhana

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ColourTests {

    @Test
    fun `Colours are (red, green, blue) tuples`() {
        val c = Colour(-0.5, 0.4, 1.7)
        assertEquals(-0.5, c.r)
        assertEquals(0.4, c.g)
        assertEquals(1.7, c.b)
    }

    @Test
    fun `Adding colours`() {
        val c1 = Colour(0.9, 0.6, 0.75)
        val c2 = Colour(0.7, 0.1, 0.25)
        val c3 = c1 + c2
        assertEquals(Colour(1.6, 0.7, 1.0), c3)
    }

    @Test
    fun `Subtracting colours`() {
        val c1 = Colour(0.9, 0.6, 0.75)
        val c2 = Colour(0.7, 0.1, 0.25)
        val c3 = c1 - c2
        assertEquals(Colour(0.2, 0.5, 0.5), c3)
    }

    @Test
    fun `Multiplying a colour by a scalar`() {
        val c1 = Colour(0.2, 0.3, 0.4)
        val c2 = c1 * 2.0
        assertEquals(Colour(0.4, 0.6, 0.8), c2)
    }

    @Test
    fun `Multiplying colours`() {
        val c1 = Colour(1.0, 0.2, 0.4)
        val c2 = Colour(0.9, 1.0, 0.1)
        val c3 = c1 * c2
        assertEquals(Colour(0.9, 0.2, 0.04), c3)
    }
}