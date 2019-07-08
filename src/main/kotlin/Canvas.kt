package com.bhana

class Canvas(val width: Int, val height: Int) {
    internal val pixels = Array(width) { Array(height) { Colour() } }

    fun writePixel(x: Int, y: Int, pixel: Colour) {
        if (!isOnCanvas(x, y)) return
        pixels[x][y] = pixel
    }

    fun getPixel(x: Int, y: Int): Colour {
        check(isOnCanvas(x, y)) { "invalid canvas coordinates ($x,$y)" }
        return pixels[x][y]
    }

    fun isOnCanvas(x: Int, y: Int): Boolean = (x >= 0) && (x < width) && (y >= 0) && (y < height)
}