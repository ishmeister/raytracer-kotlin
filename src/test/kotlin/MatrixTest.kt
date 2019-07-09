package com.bhana

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class MatrixTest {

    @Test
    fun `Constructing and inspecting a 4x4 Matrix`() {
        val m = Matrix(
            4, arrayOf(
                doubleArrayOf(1.0, 2.0, 3.0, 4.0),
                doubleArrayOf(5.5, 6.5, 7.5, 8.5),
                doubleArrayOf(9.0, 10.0, 11.0, 12.0),
                doubleArrayOf(13.5, 14.5, 15.5, 16.5)
            )
        )

        assertEquals(1.0, m[0, 0])
        assertEquals(4.0, m[0, 3])
        assertEquals(5.5, m[1, 0])
        assertEquals(7.5, m[1, 2])
        assertEquals(11.0, m[2, 2])
        assertEquals(13.5, m[3, 0])
        assertEquals(15.5, m[3, 2])
    }

    @Test
    fun `Constructing and inspecting a 2x2 Matrix`() {
        val m = Matrix(
            2, arrayOf(
                doubleArrayOf(-3.0, 5.0),
                doubleArrayOf(1.0, -2.0)
            )
        )

        assertEquals(-3.0, m[0, 0])
        assertEquals(5.0, m[0, 1])
        assertEquals(1.0, m[1, 0])
        assertEquals(-2.0, m[1, 1])
    }

    @Test
    fun `Constructing and inspecting a 3x3 Matrix`() {
        val m = Matrix(
            3, arrayOf(
                doubleArrayOf(-3.0, 5.0, 0.0),
                doubleArrayOf(1.0, -2.0, -7.0),
                doubleArrayOf(0.0, 1.0, 1.0)
            )
        )

        assertEquals(-3.0, m[0, 0])
        assertEquals(-2.0, m[1, 1])
        assertEquals(1.0, m[2, 2])
    }

    @Test
    fun `Matrix equality with identical matrices`() {
        val m1 = Matrix(
            4, arrayOf(
                doubleArrayOf(1.0, 2.0, 3.0, 4.0),
                doubleArrayOf(5.0, 6.0, 7.0, 8.0),
                doubleArrayOf(9.0, 8.0, 7.0, 6.0),
                doubleArrayOf(5.0, 4.0, 3.0, 2.0)
            )
        )
        val m2 = Matrix(
            4, arrayOf(
                doubleArrayOf(1.0, 2.0, 3.0, 4.0),
                doubleArrayOf(5.0, 6.0, 7.0, 8.0),
                doubleArrayOf(9.0, 8.0, 7.0, 6.0),
                doubleArrayOf(5.0, 4.0, 3.0, 2.0)
            )
        )
        assertEquals(m1, m2)
    }

    @Test
    fun `Matrix equality with different matrices`() {
        val m1 = Matrix(
            4, arrayOf(
                doubleArrayOf(1.0, 2.0, 3.0, 4.0),
                doubleArrayOf(5.0, 6.0, 7.0, 8.0),
                doubleArrayOf(9.0, 8.0, 7.0, 6.0),
                doubleArrayOf(5.0, 4.0, 3.0, 2.0)
            )
        )
        val m2 = Matrix(
            4, arrayOf(
                doubleArrayOf(2.0, 3.0, 4.0, 5.0),
                doubleArrayOf(6.0, 7.0, 8.0, 9.0),
                doubleArrayOf(8.0, 7.0, 6.0, 5.0),
                doubleArrayOf(4.0, 3.0, 2.0, 1.0)
            )
        )
        assertNotEquals(m1, m2)
    }

    @Test
    fun `Multiplying two matrices`() {
        val m1 = Matrix(
            4, arrayOf(
                doubleArrayOf(1.0, 2.0, 3.0, 4.0),
                doubleArrayOf(5.0, 6.0, 7.0, 8.0),
                doubleArrayOf(9.0, 8.0, 7.0, 6.0),
                doubleArrayOf(5.0, 4.0, 3.0, 2.0)
            )
        )

        val m2 = Matrix(
            4, arrayOf(
                doubleArrayOf(-2.0, 1.0, 2.0, 3.0),
                doubleArrayOf(3.0, 2.0, 1.0, -1.0),
                doubleArrayOf(4.0, 3.0, 6.0, 5.0),
                doubleArrayOf(1.0, 2.0, 7.0, 8.0)
            )
        )

        val m3 = m1 * m2

        val expected = Matrix(
            4, arrayOf(
                doubleArrayOf(20.0, 22.0, 50.0, 48.0),
                doubleArrayOf(44.0, 54.0, 114.0, 108.0),
                doubleArrayOf(40.0, 58.0, 110.0, 102.0),
                doubleArrayOf(16.0, 26.0, 46.0, 42.0)
            )
        )

        assertEquals(expected, m3)
    }

    @Test
    fun `A Matrix multiplied by a tuple`() {
        val m = Matrix(
            4, arrayOf(
                doubleArrayOf(1.0, 2.0, 3.0, 4.0),
                doubleArrayOf(2.0, 4.0, 4.0, 2.0),
                doubleArrayOf(8.0, 6.0, 4.0, 1.0),
                doubleArrayOf(0.0, 0.0, 0.0, 1.0)
            )
        )

        val t2 = m * Tuple(1.0, 2.0, 3.0, 1.0)

        assertEquals(Tuple(18.0, 24.0, 33.0, 1.0), t2)
    }

    @Test
    fun `Multiplying a matrix by the identity matrix`() {
        val m = Matrix(
            4, arrayOf(
                doubleArrayOf(0.0, 1.0, 2.0, 4.0),
                doubleArrayOf(1.0, 2.0, 4.0, 8.0),
                doubleArrayOf(2.0, 4.0, 8.0, 16.0),
                doubleArrayOf(4.0, 8.0, 16.0, 32.0)
            )
        )

        val m2 = m * identity()

        assertEquals(m, m2)
    }

    @Test
    fun `Transposing a matrix`() {
        val m = Matrix(
            4, arrayOf(
                doubleArrayOf(0.0, 9.0, 3.0, 0.0),
                doubleArrayOf(9.0, 8.0, 0.0, 8.0),
                doubleArrayOf(1.0, 8.0, 5.0, 3.0),
                doubleArrayOf(0.0, 0.0, 5.0, 8.0)
            )
        )

        val m2 = m.transpose()

        val expected = Matrix(
            4, arrayOf(
                doubleArrayOf(0.0, 9.0, 1.0, 0.0),
                doubleArrayOf(9.0, 8.0, 8.0, 0.0),
                doubleArrayOf(3.0, 0.0, 5.0, 5.0),
                doubleArrayOf(0.0, 8.0, 3.0, 8.0)
            )
        )

        assertEquals(expected, m2)
    }

    @Test
    fun `Transposing the identity matrix`() {
        assertEquals(identity(), identity().transpose())
    }
}