package com.kotrt

enum class RefractiveIndex(val index: Double) {
    VACUUM(1.0),
    AIR(1.00029),
    WATER(1.333),
    GLASS(1.52),
    DIAMOND(2.417)
}

data class Material(
    val colour: Colour = WHITE,
    val ambient: Double = 0.1,
    val diffuse: Double = 0.9,
    val specular: Double = 0.9,
    val shininess: Double = 200.0,
    val pattern: Pattern? = null,
    val reflectivity: Double = 0.0,
    val transparency: Double = 0.0,
    val refractiveIndex: Double = 1.0
) {
    init {
        require(ambient in 0.0..1.0) { "invalid ambient: required 0.0..1.0 got $ambient" }
        require(diffuse in 0.0..1.0) { "invalid diffuse: required 0.0..1.0 got $diffuse" }
        require(specular in 0.0..1.0) { "invalid specular: required 0.0..1.0 got $specular" }
        require(shininess >= 0.0) { "invalid shininess: required >= 0.0 got $shininess" }
        require(reflectivity in 0.0..1.0) { "invalid reflectivity: required 0.0..1.0 got $reflectivity" }
        require(transparency in 0.0..1.0) { "invalid transparency: required 0.0..1.0 got $transparency" }
        require(refractiveIndex >= 0.0) { "invalid refractiveIndex: required >= 0.0 got $refractiveIndex" }
    }
}