package com.bhana

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class MatrixTest {

    @Test
    fun `Constructing and inspecting a 4x4 matrix`() {
        var m = Matrix(
            arrayOf(
                arrayOf(1.0, 2.0, 3.0, 4.0),
                arrayOf(5.5, 6.5, 7.5, 8.5),
                arrayOf(9.0, 10.0, 11.0, 12.0),
                arrayOf(13.5, 14.5, 15.5, 16.5)
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
    fun `Constructing and inspecting a 2x2 matrix`() {
        var m = Matrix(
            arrayOf(
                arrayOf(-3.0, 5.0),
                arrayOf(1.0, -2.0)
            )
        )

        assertEquals(-3.0, m[0, 0])
        assertEquals(5.0, m[0, 1])
        assertEquals(1.0, m[1, 0])
        assertEquals(-2.0, m[1, 1])
    }

    @Test
    fun `Constructing and inspecting a 3x3 matrix`() {
        var m = Matrix(
            arrayOf(
                arrayOf(-3.0, 5.0, 0.0),
                arrayOf(1.0, -2.0, -7.0),
                arrayOf(0.0, 1.0, 1.0)
            )
        )
        assertEquals(-3.0, m[0, 0])
        assertEquals(-2.0, m[1, 1])
        assertEquals(1.0, m[2, 2])
    }

    @Test
    fun `Matrix equality with identical matrices`() {
        var m1 = Matrix(
            arrayOf(
                arrayOf(1.0, 2.0, 3.0, 4.0),
                arrayOf(5.0, 6.0, 7.0, 8.0),
                arrayOf(9.0, 8.0, 7.0, 6.0),
                arrayOf(5.0, 4.0, 3.0, 2.0)
            )
        )
        var m2 = Matrix(
            arrayOf(
                arrayOf(1.0, 2.0, 3.0, 4.0),
                arrayOf(5.0, 6.0, 7.0, 8.0),
                arrayOf(9.0, 8.0, 7.0, 6.0),
                arrayOf(5.0, 4.0, 3.0, 2.0)
            )
        )
        assertEquals(m1, m2)
    }

    @Test
    fun `Matrix equality with different matrices`() {
        var m1 = Matrix(
            arrayOf(
                arrayOf(1.0, 2.0, 3.0, 4.0),
                arrayOf(5.0, 6.0, 7.0, 8.0),
                arrayOf(9.0, 8.0, 7.0, 6.0),
                arrayOf(5.0, 4.0, 3.0, 2.0)
            )
        )
        var m2 = Matrix(
            arrayOf(
                arrayOf(2.0, 3.0, 4.0, 5.0),
                arrayOf(6.0, 7.0, 8.0, 9.0),
                arrayOf(8.0, 7.0, 6.0, 5.0),
                arrayOf(4.0, 3.0, 2.0, 1.0)
            )
        )
        assertNotEquals(m1, m2)
    }
}