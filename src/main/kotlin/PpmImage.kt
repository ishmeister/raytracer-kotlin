package com.bhana

import java.io.Writer
import kotlin.math.roundToInt

const val MAX_COLOUR: Int = 255
const val MAX_LINE_LENGTH: Int = 70

class PpmImage(private val canvas: Canvas) {

    private fun toOutputColour(value: Double): Int = (value * MAX_COLOUR).roundToInt()

    fun write(writer: Writer) {
        writeHeader(writer)

        for (y in 0 until canvas.height) {
            var lineLength = 0

            for (x in 0 until canvas.width) {
                val pixel = canvas[x, y].clip()

                val rStr = toOutputColour(pixel.r).toString()
                val gStr = toOutputColour(pixel.g).toString()
                val bStr = toOutputColour(pixel.b).toString()

                val components = arrayOf(rStr, gStr, bStr)

                for (i in 0 until components.size) {
                    val c = components[i]

                    writer.write(c)
                    lineLength += c.length

                    // Add a new line when c is 3rd pixel component of last row pixel OR when max line length reached
                    val eol = (i == 2 && x == canvas.width - 1) || lineLength + c.length >= MAX_LINE_LENGTH

                    if (eol) {
                        writer.write("\n")
                        lineLength = 0
                    } else {
                        writer.write(" ")
                        lineLength += 1
                    }
                }
            }
        }
    }

    private fun writeHeader(writer: Writer) {
        writer.write("P3\n")
        writer.write("${canvas.width} ${canvas.height}\n")
        writer.write("$MAX_COLOUR\n")
    }
}
