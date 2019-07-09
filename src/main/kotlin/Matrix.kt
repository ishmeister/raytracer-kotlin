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

    override fun hashCode(): Int {
        return elems.contentDeepHashCode()
    }

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


}

