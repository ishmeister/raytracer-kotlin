package com.bhana

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MatrixTests {

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
        val a = Matrix(
            4, arrayOf(
                doubleArrayOf(1.0, 2.0, 3.0, 4.0),
                doubleArrayOf(5.0, 6.0, 7.0, 8.0),
                doubleArrayOf(9.0, 8.0, 7.0, 6.0),
                doubleArrayOf(5.0, 4.0, 3.0, 2.0)
            )
        )

        val b = Matrix(
            4, arrayOf(
                doubleArrayOf(1.0, 2.0, 3.0, 4.0),
                doubleArrayOf(5.0, 6.0, 7.0, 8.0),
                doubleArrayOf(9.0, 8.0, 7.0, 6.0),
                doubleArrayOf(5.0, 4.0, 3.0, 2.0)
            )
        )

        assertEquals(a, b)
        assertTrue(a == b)
        assertFalse(a === b)
    }

    @Test
    fun `Matrix equality with different matrices`() {
        val a = Matrix(
            4, arrayOf(
                doubleArrayOf(1.0, 2.0, 3.0, 4.0),
                doubleArrayOf(5.0, 6.0, 7.0, 8.0),
                doubleArrayOf(9.0, 8.0, 7.0, 6.0),
                doubleArrayOf(5.0, 4.0, 3.0, 2.0)
            )
        )

        val b = Matrix(
            4, arrayOf(
                doubleArrayOf(2.0, 3.0, 4.0, 5.0),
                doubleArrayOf(6.0, 7.0, 8.0, 9.0),
                doubleArrayOf(8.0, 7.0, 6.0, 5.0),
                doubleArrayOf(4.0, 3.0, 2.0, 1.0)
            )
        )

        assertNotEquals(a, b)
        assertFalse(a == b)
        assertFalse(a === b)
    }

    @Test
    fun `Multiplying two matrices`() {
        val a = Matrix(
            4, arrayOf(
                doubleArrayOf(1.0, 2.0, 3.0, 4.0),
                doubleArrayOf(5.0, 6.0, 7.0, 8.0),
                doubleArrayOf(9.0, 8.0, 7.0, 6.0),
                doubleArrayOf(5.0, 4.0, 3.0, 2.0)
            )
        )

        val b = Matrix(
            4, arrayOf(
                doubleArrayOf(-2.0, 1.0, 2.0, 3.0),
                doubleArrayOf(3.0, 2.0, 1.0, -1.0),
                doubleArrayOf(4.0, 3.0, 6.0, 5.0),
                doubleArrayOf(1.0, 2.0, 7.0, 8.0)
            )
        )

        val c = a * b

        val expected = Matrix(
            4, arrayOf(
                doubleArrayOf(20.0, 22.0, 50.0, 48.0),
                doubleArrayOf(44.0, 54.0, 114.0, 108.0),
                doubleArrayOf(40.0, 58.0, 110.0, 102.0),
                doubleArrayOf(16.0, 26.0, 46.0, 42.0)
            )
        )

        assertEquals(expected, c)
    }

    @Test
    fun `A Matrix multiplied by a tuple`() {
        val a = Matrix(
            4, arrayOf(
                doubleArrayOf(1.0, 2.0, 3.0, 4.0),
                doubleArrayOf(2.0, 4.0, 4.0, 2.0),
                doubleArrayOf(8.0, 6.0, 4.0, 1.0),
                doubleArrayOf(0.0, 0.0, 0.0, 1.0)
            )
        )

        val b = a * Tuple(1.0, 2.0, 3.0, 1.0)

        assertEquals(Tuple(18.0, 24.0, 33.0, 1.0), b)
    }

    @Test
    fun `Multiplying a matrix by the identity matrix`() {
        val a = Matrix(
            4, arrayOf(
                doubleArrayOf(0.0, 1.0, 2.0, 4.0),
                doubleArrayOf(1.0, 2.0, 4.0, 8.0),
                doubleArrayOf(2.0, 4.0, 8.0, 16.0),
                doubleArrayOf(4.0, 8.0, 16.0, 32.0)
            )
        )

        val b = a * identity()

        assertEquals(a, b)
    }

    @Test
    fun `Transposing a matrix`() {
        val a = Matrix(
            4, arrayOf(
                doubleArrayOf(0.0, 9.0, 3.0, 0.0),
                doubleArrayOf(9.0, 8.0, 0.0, 8.0),
                doubleArrayOf(1.0, 8.0, 5.0, 3.0),
                doubleArrayOf(0.0, 0.0, 5.0, 8.0)
            )
        )

        val b = a.transpose()

        val expected = Matrix(
            4, arrayOf(
                doubleArrayOf(0.0, 9.0, 1.0, 0.0),
                doubleArrayOf(9.0, 8.0, 8.0, 0.0),
                doubleArrayOf(3.0, 0.0, 5.0, 5.0),
                doubleArrayOf(0.0, 8.0, 3.0, 8.0)
            )
        )

        assertEquals(expected, b)
    }

    @Test
    fun `Transposing the identity matrix`() {
        assertEquals(identity(), identity().transpose())
    }

    @Test
    fun `Calculate the determinant of a 2x2 matrix`() {
        val m = Matrix(
            2, arrayOf(
                doubleArrayOf(1.0, 5.0),
                doubleArrayOf(-3.0, 2.0)
            )
        )

        assertEquals(17.0, m.determinant())
    }

    @Test
    fun `A submatrix of a 3x3 matrix is a 2x2 matrix`() {
        val a = Matrix(
            3, arrayOf(
                doubleArrayOf(1.0, 5.0, 0.0),
                doubleArrayOf(-3.0, 2.0, 7.0),
                doubleArrayOf(0.0, 6.0, -3.0)
            )
        )

        val b = a.submatrix(0, 2)

        val expected = Matrix(
            2, arrayOf(
                doubleArrayOf(-3.0, 2.0),
                doubleArrayOf(0.0, 6.0)
            )
        )

        assertEquals(expected, b)
    }

    @Test
    fun `A submatrix of a 4x4 matrix is a 3x3 matrix`() {
        val a = Matrix(
            4, arrayOf(
                doubleArrayOf(-6.0, 1.0, 1.0, 6.0),
                doubleArrayOf(-8.0, 5.0, 8.0, 6.0),
                doubleArrayOf(-1.0, 0.0, 8.0, 2.0),
                doubleArrayOf(-7.0, 1.0, -1.0, 1.0)
            )
        )

        val b = a.submatrix(2, 1)

        val expected = Matrix(
            3, arrayOf(
                doubleArrayOf(-6.0, 1.0, 6.0),
                doubleArrayOf(-8.0, 8.0, 6.0),
                doubleArrayOf(-7.0, -1.0, 1.0)
            )
        )

        assertEquals(expected, b)
    }

    @Test
    fun `Calculating the minor of a 3x3 matrix`() {
        val a = Matrix(
            3, arrayOf(
                doubleArrayOf(3.0, 5.0, 0.0),
                doubleArrayOf(2.0, -1.0, -7.0),
                doubleArrayOf(6.0, -1.0, 5.0)
            )
        )

        val b = a.submatrix(1, 0)

        assertEquals(25.0, b.determinant())
        assertEquals(25.0, a.minor(1, 0))
    }

    @Test
    fun `Calculating a cofactor of a 3x3 matrix`() {
        val m = Matrix(
            3, arrayOf(
                doubleArrayOf(3.0, 5.0, 0.0),
                doubleArrayOf(2.0, -1.0, -7.0),
                doubleArrayOf(6.0, -1.0, 5.0)
            )
        )

        assertEquals(-12.0, m.minor(0, 0))
        assertEquals(-12.0, m.cofactor(0, 0))
        assertEquals(25.0, m.minor(1, 0))
        assertEquals(-25.0, m.cofactor(1, 0))
    }

    @Test
    fun `Calculating the determinant of a 3x3 matrix`() {
        val m = Matrix(
            3, arrayOf(
                doubleArrayOf(1.0, 2.0, 6.0),
                doubleArrayOf(-5.0, 8.0, -4.0),
                doubleArrayOf(2.0, 6.0, 4.0)
            )
        )

        assertEquals(56.0, m.cofactor(0, 0))
        assertEquals(12.0, m.cofactor(0, 1))
        assertEquals(-46.0, m.cofactor(0, 2))
        assertEquals(-196.0, m.determinant())
    }

    @Test
    fun `Calculating the determinant of a 4x4 matrix`() {
        val m = Matrix(
            4, arrayOf(
                doubleArrayOf(-2.0, -8.0, 3.0, 5.0),
                doubleArrayOf(-3.0, 1.0, 7.0, 3.0),
                doubleArrayOf(1.0, 2.0, -9.0, 6.0),
                doubleArrayOf(-6.0, 7.0, 7.0, -9.0)
            )
        )

        assertEquals(690.0, m.cofactor(0, 0))
        assertEquals(447.0, m.cofactor(0, 1))
        assertEquals(210.0, m.cofactor(0, 2))
        assertEquals(51.0, m.cofactor(0, 3))
        assertEquals(-4071.0, m.determinant())
    }

    @Test
    fun `Testing an invertible matrix for invertability`() {
        val m = Matrix(
            4, arrayOf(
                doubleArrayOf(6.0, 4.0, 4.0, 4.0),
                doubleArrayOf(5.0, 5.0, 7.0, 6.0),
                doubleArrayOf(4.0, -9.0, 3.0, -7.0),
                doubleArrayOf(9.0, 1.0, 7.0, -6.0)
            )
        )

        assertEquals(-2120.0, m.determinant())
        assertTrue(m.isInvertible())
    }

    @Test
    fun `Testing a non-invertible matrix for invertability`() {
        val m = Matrix(
            4, arrayOf(
                doubleArrayOf(-4.0, 2.0, -2.0, -3.0),
                doubleArrayOf(9.0, 6.0, 2.0, 6.0),
                doubleArrayOf(0.0, -5.0, 1.0, -5.0),
                doubleArrayOf(0.0, 0.0, 0.0, 0.0)
            )
        )

        assertEquals(0.0, m.determinant())
        assertFalse(m.isInvertible())
    }

    @Test
    fun `Calculate the inverse of a matrix`() {
        val a = Matrix(
            4, arrayOf(
                doubleArrayOf(-5.0, 2.0, 6.0, -8.0),
                doubleArrayOf(1.0, -5.0, 1.0, 8.0),
                doubleArrayOf(7.0, 7.0, -6.0, -7.0),
                doubleArrayOf(1.0, -3.0, 7.0, 4.0)
            )
        )

        assertTrue(a.isInvertible())

        val b = a.inverse()

        assertEquals(532.0, a.determinant())
        assertEquals(-160.0, a.cofactor(2, 3))
        assertEquals(-160.0 / 532.0, b[3, 2])
        assertEquals(105.0, a.cofactor(3, 2))
        assertEquals(105.0 / 532.0, b[2, 3])

        val expected = Matrix(
            4, arrayOf(
                doubleArrayOf(0.21805, 0.45113, 0.24060, -0.04511),
                doubleArrayOf(-0.80827, -1.45677, -0.44361, 0.52068),
                doubleArrayOf(-0.07895, -0.22368, -0.05263, 0.19737),
                doubleArrayOf(-0.52256, -0.81391, -0.30075, 0.30639)
            )
        )

        assertEquals(expected, b)
    }

    @Test
    fun `Calculate the inverse of second matrix`() {
        val a = Matrix(
            4, arrayOf(
                doubleArrayOf(8.0, -5.0, 9.0, 2.0),
                doubleArrayOf(7.0, 5.0, 6.0, 1.0),
                doubleArrayOf(-6.0, 0.0, 9.0, 6.0),
                doubleArrayOf(-3.0, 0.0, -9.0, -4.0)
            )
        )

        assertTrue(a.isInvertible())

        val b = a.inverse()

        val expected = Matrix(
            4, arrayOf(
                doubleArrayOf(-0.15385, -0.15385, -0.28205, -0.53846),
                doubleArrayOf(-0.07692, 0.12308, 0.02564, 0.03077),
                doubleArrayOf(0.35897, 0.35897, 0.43590, 0.92308),
                doubleArrayOf(-0.69231, -0.69231, -0.76923, -1.92308)
            )
        )

        assertEquals(expected, b)
    }

    @Test
    fun `Calculate the inverse of third matrix`() {
        val a = Matrix(
            4, arrayOf(
                doubleArrayOf(9.0, 3.0, 0.0, 9.0),
                doubleArrayOf(-5.0, -2.0, -6.0, -3.0),
                doubleArrayOf(-4.0, 9.0, 6.0, 4.0),
                doubleArrayOf(-7.0, 6.0, 6.0, 2.0)
            )
        )

        assertTrue(a.isInvertible())

        val b = a.inverse()

        val expected = Matrix(
            4, arrayOf(
                doubleArrayOf(-0.04074, -0.07778, 0.14444, -0.22222),
                doubleArrayOf(-0.07778, 0.03333, 0.36667, -0.33333),
                doubleArrayOf(-0.02901, -0.14630, -0.10926, 0.12963),
                doubleArrayOf(0.17778, 0.06667, -0.26667, 0.33333)
            )
        )

        assertEquals(expected, b)
    }

    @Test
    fun `Multiplying a product by its inverse`() {
        val a = Matrix(
            4, arrayOf(
                doubleArrayOf(3.0, -9.0, 7.0, 3.0),
                doubleArrayOf(3.0, -8.0, 2.0, -9.0),
                doubleArrayOf(-4.0, 4.0, 4.0, 1.0),
                doubleArrayOf(-6.0, 5.0, -1.0, 1.0)
            )
        )

        val b = Matrix(
            4, arrayOf(
                doubleArrayOf(8.0, 2.0, 2.0, 2.0),
                doubleArrayOf(3.0, -1.0, 7.0, 0.0),
                doubleArrayOf(7.0, 0.0, 5.0, 4.0),
                doubleArrayOf(6.0, -2.0, 0.0, 5.0)
            )
        )

        val c = a * b

        assertEquals(a, c * b.inverse())
    }
}
