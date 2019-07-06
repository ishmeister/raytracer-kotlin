package com.bhana

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.StringWriter

class PpmImageTests {

    @Test
    fun `Constructing the PPM header`() {
        val canvas = Canvas(10, 20)
        val ppm = PpmImage(canvas)
        var writer = StringWriter()
        ppm.writeCanvasToPpm(writer)
        val lines = writer.toString().lines()
        assertEquals("P3", lines[0])
        assertEquals("10 20", lines[1])
        assertEquals("255", lines[2])
    }

    @Test
    fun `Constructing the PPM pixel data`() {
        val canvas = Canvas(5, 3)
        val c1 = Colour(1.5, 0.0, 0.0)
        val c2 = Colour(0.0, 0.5, 0.0)
        val c3 = Colour(-0.5, 0.0, 1.0)

        canvas.writePixel(0, 0, c1)
        canvas.writePixel(2, 1, c2)
        canvas.writePixel(4, 2, c3)

        val ppm = PpmImage(canvas)
        var writer = StringWriter()
        ppm.writeCanvasToPpm(writer)
        val lines = writer.toString().lines()
        assertEquals("255 0 0 0 0 0 0 0 0 0 0 0 0 0 0", lines[3])
        assertEquals("0 0 0 0 0 0 0 128 0 0 0 0 0 0 0", lines[4])
        assertEquals("0 0 0 0 0 0 0 0 0 0 0 0 0 0 255", lines[5])
    }
}