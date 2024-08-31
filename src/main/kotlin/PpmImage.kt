package com.kotrt

import java.io.Writer
import kotlin.math.roundToInt

const val MAX_COLOUR: Int = 255
const val MAX_LINE_LENGTH: Int = 70

class PpmImage(private val canvas: Canvas) {
    private fun toIntPixel(value: Double): Int = (value * MAX_COLOUR).roundToInt()

    fun write(writer: Writer) {
        writeHeader(writer)

        var lineLength = 0
        for (y in 0 until canvas.height) {
            for (x in 0 until canvas.width) {
                val pixel = canvas[x, y].clip()

                val rStr = toIntPixel(pixel.r).toString()
                val gStr = toIntPixel(pixel.g).toString()
                val bStr = toIntPixel(pixel.b).toString()
                val pixelStr = "$rStr $gStr $bStr"

                val eol = lineLength + pixelStr.length >= MAX_LINE_LENGTH - 1
                if (eol) {
                    writer.write("\n")
                    lineLength = 0
                }

                if(lineLength > 0){
                    writer.write(" ")
                    lineLength++
                }

                writer.write(pixelStr)
                lineLength += pixelStr.length
            }
        }
        writer.write("\n")
        writer.flush()
    }

    private fun writeHeader(writer: Writer) {
        writer.write("P3\n")
        writer.write("${canvas.width} ${canvas.height}\n")
        writer.write("$MAX_COLOUR\n")
    }
}
