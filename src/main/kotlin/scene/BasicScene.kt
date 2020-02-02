package com.bhana.scene

import com.bhana.*
import java.io.File
import kotlin.system.measureTimeMillis

fun main() {
    val ringPattern = RingPattern(RED, BLUE)
    ringPattern.transform = scaling(0.1, 0.1, 0.1) * rotationX(Math.PI / 1.5)

    val spherePattern = StripePattern(WHITE, GREY)
    spherePattern.transform = scaling(0.1, 0.1, 0.1) * rotationX(Math.PI / 1.5)

    val middleSphere = Sphere("middleSphere")
    middleSphere.transform = translation(0.0, 1.0, 0.0)
    middleSphere.material =
        Material(
            colour = RED,
            reflectivity = 1.0
        )

    val rightSphere = Sphere("rightSphere")
    rightSphere.transform = translation(2.0, 0.5, -0.5) * scaling(0.5, 0.5, 0.5)
    rightSphere.material =
        Material(
            colour = Colour(0.5, 1.0, 0.1),
            specular = 0.6,
            pattern = ringPattern,
            reflectivity = 0.2
        )

    val leftSphere = Sphere("leftSphere")
    leftSphere.transform = translation(-1.0, 0.33, -1.0) * scaling(0.33, 0.33, 0.33)
    leftSphere.material =
        Material(
            colour = Colour(1.0, 0.8, 0.1),
            specular = 0.5,
            pattern = spherePattern,
            reflectivity = 0.2
        )

    val light1 = PointLight(point(-10.0, 10.0, -10.0), WHITE)
    val light2 = PointLight(point(10.0, 10.0, -10.0), WHITE.times(0.3))

    val world = World()
    addCheckerBox(world)
    world.shapes.add(middleSphere)
    world.shapes.add(rightSphere)
    world.shapes.add(leftSphere)
    world.lights.add(light1)
//    world.lights.add(light2)

    val camera = Camera(1600, 800, Math.PI / 3.0)
//    camera.transform = view(point(-3.0, 3.0, -5.0), point(0.0, 1.0, 0.0), vector(0.0, 1.0, 0.0))
    camera.transform = view(point(4.0, 1.5, -4.2), point(0.0, 1.0, 0.0), vector(0.0, 1.0, 0.0))

    val timeElapsed = measureTimeMillis {
        val canvas = camera.render(world)
        val ppm = PpmImage(canvas)
        val writer = File("image.ppm").bufferedWriter()
        ppm.write(writer)
    }

    println("Render time: ${timeElapsed}ms")
}

fun addCheckerBox(world: World) {
    val checkerPattern = CheckerPattern(WHITE, GREY)
    val material = Material(
        diffuse = 0.5,
        pattern = checkerPattern,
        reflectivity = 0.0
    )

    val floor = Plane("floor")
    floor.material = material.copy(reflectivity = 0.2)
    world.shapes.add(floor)

    val ceiling = Plane("ceiling")
    ceiling.transform = translation(0.0, 15.0, 0.0)
    ceiling.material = material
//    world.shapes.add(ceiling)

    val wall1 = Plane("wall1")
    wall1.transform = translation(0.0, 0.0, 15.0) * rotationX(radians(90.0))
    wall1.material = material
//    world.shapes.add(wall1)

    val wall2 = Plane("wall2")
    wall2.transform = translation(0.0, 0.0, -15.0) * rotationX(radians(90.0))
    wall2.material = material
//    world.shapes.add(wall2)

    val wall3 = Plane("wall3")
    wall3.transform = translation(15.0, 0.0, 0.0) * rotationZ(radians(90.0))
    wall3.material = material
//    world.shapes.add(wall3)

    val wall4 = Plane("wall4")
    wall4.transform = translation(-15.0, 0.0, 0.0) * rotationZ(radians(90.0))
    wall4.material = material
//    world.shapes.add(wall4)
}