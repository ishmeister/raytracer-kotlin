package com.bhana

class Canvas(val width: Int, val height: Int) {
    var pixels = Array(width) { Array(height) { Colour() } }

    fun writePixel(x: Int, y: Int, pixel: Colour) {
        pixels[x][y] = pixel
    }

    fun getPixel(x: Int, y: Int): Colour = pixels[x][y]
}