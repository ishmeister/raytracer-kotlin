package raytracer.kotlin.scene

import raytracer.kotlin.*

class BasicScene {
    fun getWorld(): World {
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
        rightSphere.transform = translation(1.5, 0.5, -0.5) * scaling(0.5, 0.5, 0.5)
        rightSphere.material =
            Material(
                colour = Colour(0.5, 1.0, 0.1),
                specular = 0.6,
                pattern = ringPattern,
                reflectivity = 0.1
            )

        val leftSphere = Sphere("leftSphere")
        leftSphere.transform = translation(-1.0, 0.33, -1.0) * scaling(0.33, 0.33, 0.33)
        leftSphere.material =
            Material(
                colour = Colour(1.0, 0.8, 0.1),
                specular = 0.5,
                pattern = spherePattern,
                reflectivity = 0.1
            )

        val light1 = PointLight(point(-10.0, 10.0, -10.0), WHITE)

        val world = World()
        addCheckerBoard(world)
        world.shapes.add(middleSphere)
        world.shapes.add(rightSphere)
        world.shapes.add(leftSphere)
        world.lights.add(light1)

        return world;
    }

    private fun addCheckerBoard(world: World) {
        val checkerPattern = CheckerPattern(WHITE, GREY)
        val material = Material(
            diffuse = 0.2,
            colour = GREY,
            specular = 0.0
        )

        val floor = Plane("floor")
        floor.material = material.copy(diffuse = 0.6, reflectivity = 0.2, pattern = checkerPattern)
        world.shapes.add(floor)
    }
}
