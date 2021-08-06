package com.kotrt

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PatternTests {

    @Test
    fun `A pattern with an object transformation`() {
        val shape = Sphere("s1")
        shape.transform = scaling(2.0, 2.0, 2.0)

        val pattern = TestPattern()
        val c = pattern.patternAtShape(shape, point(2.0, 3.0, 4.0))

        assertEquals(Colour(1.0, 1.5, 2.0), c)
    }

    @Test
    fun `A pattern with pattern transformation`() {
        val shape = Sphere("s1")
        val pattern = TestPattern()
        pattern.transform = scaling(2.0, 2.0, 2.0)

        val c = pattern.patternAtShape(shape, point(2.0, 3.0, 4.0))

        assertEquals(Colour(1.0, 1.5, 2.0), c)
    }

    @Test
    fun `A pattern with both object and pattern transformation`() {
        val shape = Sphere("s1")
        shape.transform = scaling(2.0, 2.0, 2.0)

        val pattern = TestPattern()
        pattern.transform = translation(0.5, 1.0, 1.5)

        val c = pattern.patternAtShape(shape, point(2.5, 3.0, 3.5))

        assertEquals(Colour(0.75, 0.5, 0.25), c)
    }

    @Test
    fun `Creating a stripe pattern`() {
        val pattern = StripePattern(WHITE, BLACK)

        assertEquals(WHITE, pattern.a)
        assertEquals(BLACK, pattern.b)
    }

    @Test
    fun `A stripe pattern is constant in Y`() {
        val pattern = StripePattern(WHITE, BLACK)

        assertEquals(WHITE, pattern.patternAt(point(0.0, 0.0, 0.0)))
        assertEquals(WHITE, pattern.patternAt(point(0.0, 1.0, 0.0)))
        assertEquals(WHITE, pattern.patternAt(point(0.0, 2.0, 0.0)))
    }

    @Test
    fun `A stripe pattern is constant in Z`() {
        val pattern = StripePattern(WHITE, BLACK)

        assertEquals(WHITE, pattern.patternAt(point(0.0, 0.0, 0.0)))
        assertEquals(WHITE, pattern.patternAt(point(0.0, 0.0, 1.0)))
        assertEquals(WHITE, pattern.patternAt(point(0.0, 0.0, 2.0)))
    }

    @Test
    fun `A stripe pattern alternates in X`() {
        val pattern = StripePattern(WHITE, BLACK)

        assertEquals(WHITE, pattern.patternAt(point(0.0, 0.0, 0.0)))
        assertEquals(WHITE, pattern.patternAt(point(0.9, 0.0, 0.0)))
        assertEquals(BLACK, pattern.patternAt(point(1.0, 0.0, 0.0)))
        assertEquals(BLACK, pattern.patternAt(point(-0.1, 0.0, 0.0)))
        assertEquals(BLACK, pattern.patternAt(point(-1.0, 0.0, 0.0)))
        assertEquals(WHITE, pattern.patternAt(point(-1.1, 0.0, 0.0)))
    }

    @Test
    fun `The default pattern transformation`() {
        val pattern = StripePattern(WHITE, BLACK)

        assertEquals(identity(), pattern.transform)
    }

    @Test
    fun `Assigning a transformation`() {
        val pattern = StripePattern(WHITE, BLACK)
        pattern.transform = translation(1.0, 2.0, 3.0)

        assertEquals(translation(1.0, 2.0, 3.0), pattern.transform)
    }

    @Test
    fun `A gradient linearly interpolates between colours`() {
        val pattern = GradientPattern(WHITE, BLACK)

        assertEquals(WHITE, pattern.patternAt(point(0.0, 0.0, 0.0)))
        assertEquals(Colour(0.75, 0.75, 0.75), pattern.patternAt(point(0.25, 0.0, 0.0)))
        assertEquals(Colour(0.5, 0.5, 0.5), pattern.patternAt(point(0.5, 0.0, 0.0)))
        assertEquals(Colour(0.25, 0.25, 0.25), pattern.patternAt(point(0.75, 0.0, 0.0)))
    }

    @Test
    fun `A ring should extends in both x and z`() {
        val pattern = RingPattern(WHITE, BLACK)

        assertEquals(WHITE, pattern.patternAt(point(0.0, 0.0, 0.0)))
        assertEquals(BLACK, pattern.patternAt(point(1.0, 0.0, 0.0)))
        assertEquals(BLACK, pattern.patternAt(point(0.0, 0.0, 1.0)))
        assertEquals(BLACK, pattern.patternAt(point(0.708, 0.708, 0.708)))
    }

    @Test
    fun `Checkers should repeat in x`() {
        val pattern = CheckerPattern(WHITE, BLACK)

        assertEquals(WHITE, pattern.patternAt(point(0.0, 0.0, 0.0)))
        assertEquals(WHITE, pattern.patternAt(point(0.99, 0.0, 0.0)))
        assertEquals(BLACK, pattern.patternAt(point(1.01, 0.0, 0.0)))
    }

    @Test
    fun `Checkers should repeat in y`() {
        val pattern = CheckerPattern(WHITE, BLACK)

        assertEquals(WHITE, pattern.patternAt(point(0.0, 0.0, 0.0)))
        assertEquals(WHITE, pattern.patternAt(point(0.0, 0.99, 0.0)))
        assertEquals(BLACK, pattern.patternAt(point(0.0, 1.01, 0.0)))
    }

    @Test
    fun `Checkers should repeat in z`() {
        val pattern = CheckerPattern(WHITE, BLACK)

        assertEquals(WHITE, pattern.patternAt(point(0.0, 0.0, 0.0)))
        assertEquals(WHITE, pattern.patternAt(point(0.0, 0.0, 0.99)))
        assertEquals(BLACK, pattern.patternAt(point(0.0, 0.0, 1.01)))
    }
}