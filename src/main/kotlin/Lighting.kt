package com.bhana

import kotlin.math.pow

data class PointLight(val position: Tuple, val intensity: Colour)

fun lighting(material: Material, light: PointLight, point: Tuple, eyeVec: Tuple, normalVec: Tuple): Colour {
    val effectiveColour = material.colour * light.intensity
    val lightVec = (light.position - point).normalise()

    val ambient = effectiveColour * material.ambient
    val diffuse: Colour
    val specular: Colour

    // Cosine of the angle between the light vector and normal vector
    // negative means light is on the other side of the surface
    val lightDotNormal = lightVec.dot(normalVec)

    if (lightDotNormal < 0) {
        diffuse = BLACK
        specular = BLACK
    } else {
        diffuse = effectiveColour * material.diffuse * lightDotNormal

        // Cosine of angle between reflection vector and eye vector
        // negative means light reflects away from eye
        val reflectVec = (-lightVec).reflect(normalVec)
        val reflectDotEye = reflectVec.dot(eyeVec)

        specular = if (reflectDotEye <= 0) {
            BLACK
        } else {
            val factor = reflectDotEye.pow(material.shininess)
            light.intensity * material.specular * factor
        }
    }

    return ambient + diffuse + specular
}

