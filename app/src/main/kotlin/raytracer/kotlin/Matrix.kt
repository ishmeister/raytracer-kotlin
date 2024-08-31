package raytracer.kotlin

class Matrix(private val size: Int, val elements: Array<DoubleArray>) {
    init {
        require(size > 0 && size == elements.size && size == elements[0].size) { "invalid matrix of size: $size" }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Matrix

        if (elements.size != other.elements.size) return false

        for (row in 0 until elements.size) {
            for (col in 0 until elements[row].size) {
                if (!elements[row][col].eq(other.elements[row][col]))
                    return false
            }
        }
        return true
    }

    override fun hashCode(): Int = elements.contentDeepHashCode()

    override fun toString(): String {
        val builder = StringBuilder()

        for (row in elements) {
            for (col in row) {
                builder.append("$col ")
            }
            builder.append("\n")
        }
        return "Matrix(size=$size, elements=\n$builder)"
    }

    operator fun get(row: Int, col: Int): Double = elements[row][col]

    operator fun set(row: Int, col: Int, value: Double) {
        elements[row][col] = value
    }

    operator fun times(other: Matrix): Matrix {
        val m = Array(size) { DoubleArray(size) { 0.0 } }

        for (row in 0 until size) {
            for (col in 0 until size) {
                m[row][col] = elements[row][0] * other.elements[0][col] +
                        elements[row][1] * other.elements[1][col] +
                        elements[row][2] * other.elements[2][col] +
                        elements[row][3] * other.elements[3][col]
            }
        }
        return Matrix(size, m)
    }

    operator fun times(other: Tuple): Tuple {
        val x =
            elements[0][0] * other.x + elements[0][1] * other.y + elements[0][2] * other.z + elements[0][3] * other.w
        val y =
            elements[1][0] * other.x + elements[1][1] * other.y + elements[1][2] * other.z + elements[1][3] * other.w
        val z =
            elements[2][0] * other.x + elements[2][1] * other.y + elements[2][2] * other.z + elements[2][3] * other.w
        val w =
            elements[3][0] * other.x + elements[3][1] * other.y + elements[3][2] * other.z + elements[3][3] * other.w
        return Tuple(x, y, z, w)
    }

    fun transpose(): Matrix {
        val m = Array(size) { DoubleArray(size) { 0.0 } }

        for (row in 0 until size) {
            for (col in 0 until size) {
                m[col][row] = elements[row][col]
            }
        }
        return Matrix(size, m)
    }

    fun submatrix(row: Int, col: Int): Matrix {
        val m = Array(size - 1) { DoubleArray(size - 1) { 0.0 } }

        var ix = 0
        var iy = 0

        for (x in 0 until size) {
            if (x == row) continue
            for (y in 0 until size) {
                if (y == col) continue
                m[ix][iy++] = elements[x][y]
            }
            iy = 0
            ix++
        }
        return Matrix(size - 1, m)
    }

    fun determinant(): Double {
        var determinant = 0.0
        if (size == 2) {
            determinant = elements[0][0] * elements[1][1] - elements[0][1] * elements[1][0]
        } else {
            for (col in 0 until size) {
                determinant += elements[0][col] * cofactor(0, col)
            }
        }
        return determinant
    }

    fun minor(row: Int, col: Int): Double = submatrix(row, col).determinant()
    fun cofactor(row: Int, col: Int): Double = if ((row + col) % 2.0 == 0.0) minor(row, col) else -minor(row, col)
    fun isInvertible(): Boolean = determinant() != 0.0

    fun inverse(): Matrix {
        check(isInvertible())

        val m = Array(size) { DoubleArray(size) { 0.0 } }

        for (row in 0 until size) {
            for (col in 0 until size) {
                val c = cofactor(row, col)
                m[col][row] = c / determinant()
            }
        }

        return Matrix(size, m)
    }

    fun translate(x: Double, y: Double, z: Double): Matrix = this * translation(x, y, z)
    fun scale(x: Double, y: Double, z: Double): Matrix = this * scaling(x, y, z)
    fun rotateX(r: Double): Matrix = this * rotationX(r)
    fun rotateY(r: Double): Matrix = this * rotationY(r)
    fun rotateZ(r: Double): Matrix = this * rotationZ(r)
    fun shear(xy: Double, xz: Double, yx: Double, yz: Double, zx: Double, zy: Double) =
        this * shearing(xy, xz, yx, yz, zx, zy)
}
