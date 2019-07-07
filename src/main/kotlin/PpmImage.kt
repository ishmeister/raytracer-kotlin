package com.bhana

import java.io.Writer
import kotlin.math.roundToInt

const val MAX_COLOUR: Int = 255
const val MAX_LINE_LENGTH: Int = 70

class PpmImage(val canvas: Canvas) {

    private fun toScaledInt(value: Double): Int = (value * MAX_COLOUR).roundToInt()

    fun writeCanvasToPpm(writer: Writer) {
        writer.write("P3\n")
        writer.write("${canvas.width} ${canvas.height}\n")
        writer.write("$MAX_COLOUR\n")

        for (y in 0 until canvas.height) {

            var builder = StringBuilder()
            var lineLength = 0

            for (x in 0 until canvas.width) {
                val pixel = canvas.getPixel(x, y).clip()

                val rStr = toScaledInt(pixel.r).toString()
                val gStr = toScaledInt(pixel.g).toString()
                val bStr = toScaledInt(pixel.b).toString()

                val components = arrayOf(rStr, gStr, bStr)
                for (i in 0 until components.size) {
                    val c = components[i]

                    builder.append(c)
                    lineLength += c.length

                    // 3rd pixel component of last pixel in the row or max line length reached
                    val eol = (i == 2 && x == canvas.width - 1) || lineLength + c.length >= MAX_LINE_LENGTH
                    if (eol) {
                        builder.append("\n")
                        lineLength = 0
                    } else {
                        builder.append(" ")
                        lineLength += 1
                    }
                }
            }

            writer.write(builder.toString())
        }
    }
}