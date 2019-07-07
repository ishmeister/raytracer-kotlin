package com.bhana

class Projectile(val position: Tuple, val velocity: Tuple)
class Environment(val gravity: Tuple, val wind: Tuple)

fun tick(env: Environment, proj: Projectile): Projectile {
    val position = proj.position + proj.velocity
    val velocity = proj.velocity + env.gravity + env.wind
    return Projectile(position, velocity)
}