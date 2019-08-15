package com.bhana

data class Material(
    val colour: Colour = WHITE,
    val ambient: Double = 0.1,
    val diffuse: Double = 0.9,
    val specular: Double = 0.9,
    val shininess: Double = 200.0
) {
    init {
        require(ambient in 0.0..1.0) { "invalid ambient: required 0.0..1.0 got $ambient" }
        require(diffuse in 0.0..1.0) { "invalid diffuse: required 0.0..1.0 got $diffuse" }
        require(specular in 0.0..1.0) { "invalid specular: required 0.0..1.0 got $specular" }
        require(shininess >= 0.0) { "invalid shininess: required >= 0.0 got $shininess" }
    }
}