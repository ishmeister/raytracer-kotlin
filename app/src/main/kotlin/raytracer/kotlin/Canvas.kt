package raytracer.kotlin

class Canvas(val width: Int, val height: Int) {
    val pixels = Array(width) { Array(height) { Colour() } }

    init {
        require(width > 0) { "invalid width: width must be a > 0 $width" }
        require(height > 0) { "invalid height: height must be > 0 $height" }
    }

    fun isOnCanvas(x: Int, y: Int): Boolean = (x >= 0) && (x < width) && (y >= 0) && (y < height)

    operator fun get(x: Int, y: Int): Colour {
        check(isOnCanvas(x, y)) { "invalid canvas coordinates ($x,$y)" }
        return pixels[x][y]
    }

    operator fun set(x: Int, y: Int, pixel: Colour) {
        if (!isOnCanvas(x, y)) {
            return
        }
        pixels[x][y] = pixel
    }
}
