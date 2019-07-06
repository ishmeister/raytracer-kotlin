package com.bhana

import java.io.Writer
import kotlin.math.roundToInt

const val MAX_COLOUR: Int = 255
const val MAX_LINE_LENGTH: Int = 70

class PpmImage(val canvas: Canvas) {

    private fun toScaledInt(value: Double): Int = clip((value * MAX_COLOUR).roundToInt())

    private fun clip(value: Int): Int {
        return when {
            value > MAX_COLOUR -> MAX_COLOUR
            value < 0 -> 0
            else -> value
        }
    }

    fun writeCanvasToPpm(writer: Writer) {
        writer.write("P3\n")
        writer.write("${canvas.width} ${canvas.height}\n")
        writer.write("$MAX_COLOUR\n")

        for (y in 0 until canvas.pixels.size) {
            for (x in 0 until canvas.pixels[y].size) {
                val pixel = canvas.getPixel(x, y)

                writer.write("${toScaledInt(pixel.r)} ")
                writer.write("${toScaledInt(pixel.g)} ")
                writer.write("${toScaledInt(pixel.b)}")

                if (x > 0 && x % MAX_LINE_LENGTH == 0) {
                    writer.write("\n")
                }

                if (x < canvas.pixels[y].size - 1) {
                    writer.write(" ")
                }
            }
            writer.write("\n")
        }
    }
}