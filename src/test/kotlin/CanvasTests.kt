package com.bhana

import org.junit.jupiter.api.Assertions.*
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
        c[2, 3] = Colour(1.0, 0.0, 0.0)
        assertEquals(Colour(1.0, 0.0, 0.0), c[2, 3])
    }

    @Test
    fun `Writing positive off Canvas pixels does not throw exception`() {
        val c = Canvas(10, 20)
        c[-10, -20] = Colour(1.0, 0.0, 0.0)
    }

    @Test
    fun `Writing negative off Canvas pixels does not throw exception`() {
        val c = Canvas(10, 20)
        assertFalse(c.isOnCanvas(10, 20))
        c[-10, -20] = Colour(1.0, 0.0, 0.0)
    }

    @Test
    fun `Getting off canvas pixel throws exception`() {
        val c = Canvas(10, 20)
        assertFalse(c.isOnCanvas(10, 20))

        val exception = assertThrows(IllegalStateException::class.java) {
            c[10, 20]
        }
        assertNotNull(exception)
    }
}