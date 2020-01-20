package com.bhana

import kotlin.math.pow

data class PointLight(val position: Tuple, val intensity: Colour) {

    fun lighting(
        material: Material,
        shape: Shape,
        point: Tuple,
        eyeVec: Tuple,
        normalVec: Tuple,
        inShadow: Boolean
    ): Colour {
        val colour = material.pattern?.stripeAtObject(shape, point) ?: material.colour

        val effectiveColour = colour * intensity
        val ambient = effectiveColour * material.ambient

        if (inShadow) return ambient

        val diffuse: Colour
        val specular: Colour

        val lightVec = (position - point).normalise()

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
                intensity * material.specular * factor
            }
        }

        return ambient + diffuse + specular
    }
}

