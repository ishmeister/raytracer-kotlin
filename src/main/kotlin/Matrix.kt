package com.bhana

class Matrix(val size: Int, val elems: Array<DoubleArray>) {

    init {
        require(size > 0 && size == elems.size && size == elems[0].size) { "invalid matrix of size: $size" }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Matrix

        if (elems.size != other.elems.size) return false

        for (row in 0 until elems.size) {
            for (col in 0 until elems[row].size) {
                if (!elems[row][col].eq(other.elems[row][col]))
                    return false
            }
        }
        return true
    }

    override fun hashCode(): Int = elems.contentDeepHashCode()

    override fun toString(): String {
        var builder = StringBuilder()

        for (row in elems) {
            for (col in row) {
                builder.append("$col ")
            }
            builder.append("\n")
        }
        return "Matrix(size=$size, elems=\n$builder)"
    }

    operator fun get(row: Int, col: Int): Double = elems[row][col]

    operator fun set(row: Int, col: Int, value: Double) {
        elems[row][col] = value
    }

    operator fun times(other: Matrix): Matrix {
        val m = Array(size) { DoubleArray(size) { 0.0 } }

        for (row in 0 until size) {
            for (col in 0 until size) {
                m[row][col] = elems[row][0] * other.elems[0][col] +
                        elems[row][1] * other.elems[1][col] +
                        elems[row][2] * other.elems[2][col] +
                        elems[row][3] * other.elems[3][col]
            }
        }
        return Matrix(size, m)
    }

    operator fun times(other: Tuple): Tuple {
        val x = elems[0][0] * other.x + elems[0][1] * other.y + elems[0][2] * other.z + elems[0][3] * other.w
        val y = elems[1][0] * other.x + elems[1][1] * other.y + elems[1][2] * other.z + elems[1][3] * other.w
        val z = elems[2][0] * other.x + elems[2][1] * other.y + elems[2][2] * other.z + elems[2][3] * other.w
        val w = elems[3][0] * other.x + elems[3][1] * other.y + elems[3][2] * other.z + elems[3][3] * other.w
        return Tuple(x, y, z, w)
    }

    fun transpose(): Matrix {
        val m = Array(size) { DoubleArray(size) { 0.0 } }

        for (row in 0 until size) {
            for (col in 0 until size) {
                m[col][row] = elems[row][col]
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
                m[ix][iy++] = elems[x][y]
            }
            iy = 0
            ix++
        }
        return Matrix(size - 1, m)
    }

    fun determinant(): Double {
        var determinant = 0.0
        if (size == 2) {
            determinant = elems[0][0] * elems[1][1] - elems[0][1] * elems[1][0]
        } else {
            for (col in 0 until size) {
                determinant += elems[0][col] * cofactor(0, col)
            }
        }
        return determinant
    }

    fun minor(row: Int, col: Int): Double = submatrix(row, col).determinant()
    fun cofactor(row: Int, col: Int): Double = if ((row + col) % 2 == 0) minor(row, col) else -minor(row, col)
    fun isInvertable(): Boolean = !determinant().eq(0.0)

    fun inverse(): Matrix {
        check(isInvertable())

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
