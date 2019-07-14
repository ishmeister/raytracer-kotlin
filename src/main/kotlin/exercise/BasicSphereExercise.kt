package com.bhana.exercise

import com.bhana.*
import java.io.File

fun main() {

    val colour = Colour(1.0, 0.0, 0.0)
    val shape = Sphere("sphere1")

    val rayOrigin = point(0.0, 0.0, -5.0)
    val wallZ = 10.0
    val wallSize = 7.0
    val canvasPixels = 300
    val pixelSize = wallSize / canvasPixels
    val half = wallSize / 2.0

    val canvas = Canvas(canvasPixels, canvasPixels)

//    shape.transform = scaling(10.0, 10.0, 10.0)

    for (y in 0 until canvasPixels) {
        val worldY = half - pixelSize * y

        for (x in 0 until canvasPixels) {
            val worldX = -half + pixelSize * x

            // ray target
            val position = point(worldX, worldY, wallZ)

            val ray = Ray(rayOrigin, (position - rayOrigin).normalise())

            val intersects = shape.intersect(ray)
            val hit = hit(intersects)

            if (hit != null) {
                canvas[x, y] = colour
            }
        }
    }

    val ppm = PpmImage(canvas)
    var writer = File("image.ppm").bufferedWriter()

    ppm.writeCanvasToPpm(writer)
}