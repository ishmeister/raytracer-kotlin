package com.bhana

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MathTests {

    @Test
    fun `Equality with floating point arithmetic`() {
        assertTrue((1.1 + 2.2).eq(3.3))
    }

    @Test
    fun `Equality with floating point 0 and -0`() {
        assertTrue(0.0.eq(-0.0))
    }
}
