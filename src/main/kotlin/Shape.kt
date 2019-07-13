package com.bhana

interface Intersect {
    fun intersect(ray: Ray): List<Double>
}

abstract class Shape : Intersect