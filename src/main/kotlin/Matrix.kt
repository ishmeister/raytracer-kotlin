package com.bhana

class Matrix(val elems: Array<Array<Double>>) {

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
                builder.append(col).append(" ")
            }
            builder.append("\n")
        }
        return "Matrix(elems=\n$builder)"
    }

    operator fun get(row: Int, col: Int): Double = elems[row][col]
    operator fun set(row: Int, col: Int, value: Double) {
        elems[row][col] = value
    }
}

