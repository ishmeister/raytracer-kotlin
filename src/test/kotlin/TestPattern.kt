package com.bhana

class TestPattern : Pattern() {
    override fun patternAt(point: Tuple): Colour = Colour(point.x, point.y, point.z)
}