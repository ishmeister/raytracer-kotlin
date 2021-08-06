package com.kotrt

import kotlin.math.tan

class Camera(val hSize: Int, val vSize: Int, val fieldOfView: Double) {
    val pixelSize: Double

    private val halfWidth: Double
    private val halfHeight: Double

    var transform: Matrix = identity()
        set(value) {
            field = value
            inverseTransform = value.inverse()
        }

    var inverseTransform: Matrix = transform.inverse()
        private set

    init {
        require(hSize > 0) { "invalid hSize: hSize must be > 0 $hSize" }
        require(vSize > 0) { "invalid vSize: vSize must be > 0 $vSize" }

        val aspect = hSize.toDouble() / vSize.toDouble()
        val halfView = tan(fieldOfView / 2.0)

        if (aspect >= 1.0) {
            halfWidth = halfView
            halfHeight = halfView / aspect
        } else {
            halfWidth = halfView * aspect
            halfHeight = halfView
        }

        pixelSize = (halfWidth * 2.0) / hSize
    }

    fun rayForPixel(px: Int, py: Int): Ray {
        val xOffset = (px + 0.5) * pixelSize
        val yOffset = (py + 0.5) * pixelSize
        val worldX = halfWidth - xOffset
        val worldY = halfHeight - yOffset

        val pixel = inverseTransform * point(worldX, worldY, -1.0)
        val origin = inverseTransform * ORIGIN
        val direction = (pixel - origin).normalise()

        return Ray(origin, direction)
    }

    fun render(world: World): Canvas {
        val canvas = Canvas(hSize, vSize)

        for (y in 0 until vSize) {
            for (x in 0 until hSize) {
                val ray = rayForPixel(x, y)
                val colour = world.colourAt(ray)
                canvas[x, y] = colour
            }
        }

        return canvas
    }
}