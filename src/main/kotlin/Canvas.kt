package com.bhana

class Canvas(val width: Int, val height: Int) {
    internal var pixels = Array(width) { Array(height) { Colour() } }

    fun writePixel(x: Int, y: Int, pixel: Colour) {
        if (!isOnCanvas(x, y))
            throw IllegalArgumentException("invalid canvas coordinates ($x,$y)")
        pixels[x][y] = pixel
    }

    fun getPixel(x: Int, y: Int): Colour {
        if (!isOnCanvas(x, y))
            throw IllegalArgumentException("invalid canvas coordinates ($x,$y)")
        return pixels[x][y]
    }

    fun isOnCanvas(x: Int, y: Int): Boolean = (x >= 0) && (x < width) && (y >= 0) && (y < height)
}