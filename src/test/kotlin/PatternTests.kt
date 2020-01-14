package com.bhana

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PatternTests {

    @Test
    fun `Creating a stripe pattern`() {
        val pattern = StripePattern(WHITE, BLACK)
        assertEquals(WHITE, pattern.a)
        assertEquals(BLACK, pattern.b)
    }

    @Test
    fun `A stripe pattern is constant in Y`() {
        val pattern = StripePattern(WHITE, BLACK)
        assertEquals(WHITE, pattern.stripeAt(point(0.0, 0.0, 0.0)))
        assertEquals(WHITE, pattern.stripeAt(point(0.0, 1.0, 0.0)))
        assertEquals(WHITE, pattern.stripeAt(point(0.0, 2.0, 0.0)))
    }

    @Test
    fun `A stripe pattern is constant in Z`() {
        val pattern = StripePattern(WHITE, BLACK)
        assertEquals(WHITE, pattern.stripeAt(point(0.0, 0.0, 0.0)))
        assertEquals(WHITE, pattern.stripeAt(point(0.0, 0.0, 1.0)))
        assertEquals(WHITE, pattern.stripeAt(point(0.0, 0.0, 2.0)))
    }

    @Test
    fun `A stripe pattern alternates in X`() {
        val pattern = StripePattern(WHITE, BLACK)
        assertEquals(WHITE, pattern.stripeAt(point(0.0, 0.0, 0.0)))
        assertEquals(WHITE, pattern.stripeAt(point(0.9, 0.0, 0.0)))
        assertEquals(BLACK, pattern.stripeAt(point(1.0, 0.0, 0.0)))
        assertEquals(BLACK, pattern.stripeAt(point(-0.1, 0.0, 0.0)))
        assertEquals(BLACK, pattern.stripeAt(point(-1.0, 0.0, 0.0)))
        assertEquals(WHITE, pattern.stripeAt(point(-1.1, 0.0, 0.0)))
    }
}