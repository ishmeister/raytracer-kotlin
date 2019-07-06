package com.bhana

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CanvasTests {

    @Test
    fun `Creating a Canvas`() {
        val c = Canvas(10, 20)
        assertEquals(10, c.width)
        assertEquals(20, c.height)
        assertEquals(10, c.pixels.size)
        assertEquals(20, c.pixels[0].size)
        for (row in c.pixels) {
            for (pixel in row) {
                assertEquals(Colour(0.0, 0.0, 0.0), pixel)
            }
        }
    }

    @Test
    fun `Writing pixels to a Canvas`() {
        val c = Canvas(10, 20)
        c.writePixel(2, 3, Colour(1.0, 0.0, 0.0))
        assertEquals(Colour(1.0, 0.0, 0.0), c.getPixel(2, 3))
    }
}