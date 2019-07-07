package com.bhana

class Projectile(val position: Tuple, val velocity: Tuple) {
    override fun toString(): String {
        return "Projectile(position=$position, velocity=$velocity)"
    }
}

class Environment(val gravity: Tuple, val wind: Tuple) {
    override fun toString(): String {
        return "Environment(gravity=$gravity, wind=$wind)"
    }
}

fun tick(env: Environment, proj: Projectile): Projectile {
    val position = proj.position + proj.velocity
    val velocity = proj.velocity + env.gravity + env.wind
    return Projectile(position, velocity)
}

fun main() {
    var p = Projectile(point(0.0, 1.0, 0.0), vector(1.0, 1.0, 0.0).normalise())
    val e = Environment(vector(0.0, -0.1, 0.0), vector(-0.01, 0.0, 0.0))

    println(e)
    println(p)

    while (p.position.y > 0.0) {
        p = tick(e, p)
        println(p)
    }
}