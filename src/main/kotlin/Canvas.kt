package com.bhana

class Canvas(val width: Int, val height: Int) {
    var pixels = Array(height) { Array(width) { Colour() } }

    fun writePixel(x: Int, y: Int, pixel: Colour) {
        pixels[y][x] = pixel
    }

    fun getPixel(x: Int, y: Int): Colour = pixels[y][x]
}