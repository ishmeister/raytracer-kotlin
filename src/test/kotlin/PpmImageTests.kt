package com.kotrt

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.StringWriter

class PpmImageTests {

    @Test
    fun `Constructing the PPM header`() {
        val canvas = Canvas(10, 20)
        val ppm = PpmImage(canvas)
        val writer = StringWriter()

        ppm.write(writer)

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

        canvas[0, 0] = c1
        canvas[2, 1] = c2
        canvas[4, 2] = c3

        val ppm = PpmImage(canvas)
        val writer = StringWriter()

        ppm.write(writer)

        val lines = writer.toString().lines()
        assertEquals(6, lines.size)
        assertEquals("255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 128 0 0 0 0 0 0 0 0 0 0", lines[3])
        assertEquals("0 0 0 0 0 0 0 0 0 0 0 255", lines[4])
        assertEquals("", lines[5])
    }

    @Test
    fun `All PPM lines are under 70 characters in length`() {
        val canvas = Canvas(10, 2)
        for (x in 0 until canvas.width) {
            for (y in 0 until canvas.height) {
                canvas[x, y] = Colour(1.0, 0.8, 0.6)
            }
        }

        val ppm = PpmImage(canvas)
        val writer = StringWriter()

        ppm.write(writer)

        val lines = writer.toString().lines()
        lines.forEach { line ->
            assertTrue(line.length <= 70, "line length must be <= 70: size=${line.length}, line=$line")
        }
    }

    @Test
    fun `PPM file is terminated by newline character`() {
        val canvas = Canvas(5, 3)
        val ppm = PpmImage(canvas)
        val writer = StringWriter()

        ppm.write(writer)

        val file = writer.toString()
        assertEquals('\n', file.last())
    }
}
