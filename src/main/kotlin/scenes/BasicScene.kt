package com.bhana.scenes

import com.bhana.*
import java.io.File

fun main() {
    val world = World()

    val floor = Sphere("floor")
    floor.transform = scaling(10.0, 0.01, 10.0)
    floor.material = Material(colour = Colour(1.0, 0.9, 0.9), specular = 0.0)
    world.shapes.add(floor)

    val leftWall = Sphere("leftWall")
    leftWall.transform = translation(0.0, 0.0, 5.0) *
            rotationY(-Math.PI / 4.0) *
            rotationX(Math.PI / 2.0) *
            scaling(10.0, 0.01, 10.0)
    leftWall.material = floor.material
    world.shapes.add(leftWall)

    val rightWall = Sphere("rightWall")
    rightWall.transform = translation(0.0, 0.0, 5.0) *
            rotationY(Math.PI / 4.0) *
            rotationX(Math.PI / 2.0) *
            scaling(10.0, 0.01, 10.0)
    rightWall.material = floor.material
    world.shapes.add(rightWall)

    val middle = Sphere("middle")
    middle.transform = translation(-0.5, 1.0, 0.5)
    middle.material = Material(colour = Colour(0.1, 1.0, 0.5), diffuse = 0.7, specular = 0.3)
    world.shapes.add(middle)

    val right = Sphere("right")
    right.transform = translation(1.5, 0.5, -0.5) * scaling(0.5, 0.5, 0.5)
    right.material = Material(colour = Colour(0.5, 1.0, 0.1), diffuse = 0.7, specular = 0.3)
    world.shapes.add(right)

    val left = Sphere("left")
    left.transform = translation(-1.5, 0.33, -0.75) * scaling(0.33, 0.33, 0.33)
    left.material = Material(colour = Colour(1.0, 0.8, 0.1), diffuse = 0.7, specular = 0.3)
    world.shapes.add(left)

    val light = PointLight(point(-10.0, 10.0, -10.0), WHITE)
    world.lights.add(light)

    val camera = Camera(600, 400, Math.PI / 3.0)
    camera.transform = view(point(0.0, 1.5, -5.0), point(0.0, 1.0, 0.0), vector(0.0, 1.0, 0.0))

    val canvas = camera.render(world)

    val ppm = PpmImage(canvas)
    var writer = File("image.ppm").bufferedWriter()

    ppm.write(writer)
}