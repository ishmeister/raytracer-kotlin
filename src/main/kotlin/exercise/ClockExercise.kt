package com.bhana.exercise

import com.bhana.*
import java.io.File

fun main() {
    val canvas = Canvas(600, 600)
    val colour = Colour(1.0, 1.0, 1.0)
    var point = point(0.0, 150.0, 0.0)
    var t = identity()

    for (i in 0 until 12) {
        point = t.rotateZ(radians(360.0 / 12.0)) * point
        canvas[point.x.toInt() + canvas.width / 2, point.y.toInt() + canvas.height / 2] = colour
    }

    canvas[canvas.width / 2, canvas.height / 2] = Colour(1.0, 0.0, 0.0)

    val ppm = PpmImage(canvas)
    var writer = File("image.ppm").bufferedWriter()

    ppm.write(writer)
}