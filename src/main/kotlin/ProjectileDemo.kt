package com.bhana

import java.io.File

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
    val start = point(0.0, 1.0, 0.0)
    val velocity = vector(1.0, 1.8, 0.0).normalise() * 11.25
    var proj = Projectile(start, velocity)

    val gravity = vector(0.0, -0.1, 0.0)
    val wind = vector(-0.01, 0.0, 0.0)

    val env = Environment(gravity, wind)
    val canvas = Canvas(900, 550)
    val colour = Colour(1.0, 0.0, 0.0)

    while (proj.position.y > 0.0) {
        proj = tick(env, proj)
        val x = proj.position.x.toInt()
        val y = canvas.height - proj.position.y.toInt()
        canvas[x, y] = colour
    }

    val ppm = PpmImage(canvas)
    var writer = File("image.ppm").bufferedWriter()

    ppm.writeCanvasToPpm(writer)
}