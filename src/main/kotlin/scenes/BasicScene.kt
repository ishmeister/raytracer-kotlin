package com.bhana.scenes

import com.bhana.*
import java.io.File
import kotlin.system.measureTimeMillis

fun main() {
    val floor = Sphere("floor")
    floor.transform = scaling(10.0, 0.01, 10.0)
    floor.material = Material(colour = Colour(1.0, 0.9, 0.9), specular = 0.0)

    val leftWall = Sphere("leftWall")
    leftWall.transform = translation(0.0, 0.0, 5.0) *
            rotationY(-Math.PI / 4.0) *
            rotationX(Math.PI / 2.0) *
            scaling(10.0, 0.01, 10.0)
    leftWall.material = floor.material

    val rightWall = Sphere("rightWall")
    rightWall.transform = translation(0.0, 0.0, 5.0) *
            rotationY(Math.PI / 4.0) *
            rotationX(Math.PI / 2.0) *
            scaling(10.0, 0.01, 10.0)
    rightWall.material = floor.material

    val middleSphere = Sphere("middleSphere")
    middleSphere.transform = translation(-0.5, 1.0, 0.5)
    middleSphere.material = Material(colour = Colour(0.1, 1.0, 0.5), diffuse = 0.7, specular = 0.3)

    val rightSphere = Sphere("rightSphere")
    rightSphere.transform = translation(1.5, 0.5, -0.5) * scaling(0.5, 0.5, 0.5)
    rightSphere.material = Material(colour = Colour(0.5, 1.0, 0.1), diffuse = 0.7, specular = 0.3)

    val leftSphere = Sphere("leftSphere")
    leftSphere.transform = translation(-1.5, 0.33, -0.75) * scaling(0.33, 0.33, 0.33)
    leftSphere.material = Material(colour = Colour(1.0, 0.8, 0.1), diffuse = 0.7, specular = 0.3)

    val light = PointLight(point(-10.0, 10.0, -10.0), WHITE)

    val world = World()
    world.shapes.add(floor)
    world.shapes.add(leftWall)
    world.shapes.add(rightWall)
    world.shapes.add(middleSphere)
    world.shapes.add(rightSphere)
    world.shapes.add(leftSphere)
    world.lights.add(light)

    val camera = Camera(600, 400, Math.PI / 3.0)
    camera.transform = view(point(0.0, 1.5, -5.0), point(0.0, 1.0, 0.0), vector(0.0, 1.0, 0.0))

    val timeElapsed = measureTimeMillis {
        val canvas = camera.render(world)
        val ppm = PpmImage(canvas)
        var writer = File("image.ppm").bufferedWriter()
        ppm.write(writer)
    }

    println("Render time: ${timeElapsed}ms")
}