package com.bhana

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

class CameraTests {

    @Test
    fun `Constructing a camera`() {
        val hSize = 160
        val vSize = 120
        val fieldOfView = Math.PI / 2.0
        val c = Camera(hSize, vSize, fieldOfView)

        assertEquals(160, c.hSize)
        assertEquals(120, c.vSize)
        assertEquals(Math.PI / 2.0, c.fieldOfView)
        assertEquals(identity(), c.transform)
    }

    @Test
    fun `The pixel size for a horizontal canvas`() {
        val c = Camera(200, 125, Math.PI / 2.0)
        assertEquals(0.01, c.pixelSize, EPSILON)
    }

    @Test
    fun `The pixel size for a vertical canvas`() {
        val c = Camera(125, 200, Math.PI / 2.0)
        assertEquals(0.01, c.pixelSize, EPSILON)
    }

    @Test
    fun `Constructing a ray though the center of the canvas`() {
        val c = Camera(201, 101, Math.PI / 2.0)
        val r = c.rayForPixel(100, 50)

        assertEquals(point(0.0, 0.0, 0.0), r.origin)
        assertEquals(vector(0.0, 0.0, -1.0), r.direction)
    }

    @Test
    fun `Constructing a ray through the corner of the canvas`() {
        val c = Camera(201, 101, Math.PI / 2.0)
        val r = c.rayForPixel(0, 0)

        assertEquals(point(0.0, 0.0, 0.0), r.origin)
        assertEquals(vector(0.66519, 0.33259, -0.66851), r.direction)
    }

    @Test
    fun `Constructing a ray when the camera is transformed`() {
        val c = Camera(201, 101, Math.PI / 2.0)
        c.transform = rotationY(Math.PI / 4.0) * translation(0.0, -2.0, 5.0)

        val r = c.rayForPixel(100, 50)

        assertEquals(point(0.0, 2.0, -5.0), r.origin)
        assertEquals(vector(sqrt(2.0) / 2.0, 0.0, -(sqrt(2.0) / 2.0)), r.direction)
    }

    @Test
    fun `Rending a world with a camera`() {
        val w = defaultWorld()
        val from = point(0.0, 0.0, -5.0)
        val to = point(0.0, 0.0, 0.0)
        val up = vector(0.0, 1.0, 0.0)

        val c = Camera(11, 11, Math.PI / 2.0)
        c.transform = view(from, to, up)

        val canvas = c.render(w)

        assertEquals(Colour(0.38066, 0.47583, 0.2855), canvas[5, 5])
    }
}