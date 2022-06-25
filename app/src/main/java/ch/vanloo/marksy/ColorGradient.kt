package ch.vanloo.marksy

import androidx.compose.ui.graphics.Color

class ColorGradient(
    private val start: Color,
    private val end: Color,
    private val steppingDistance: Float,
) {
    fun calculate(step: Float): Color {
        val redSteppingDistance = (end.red - start.red) / steppingDistance
        val greenSteppingDistance = (end.green - start.green) / steppingDistance
        val blueSteppingDistance = (end.blue - start.blue) / steppingDistance

        val redStep = redSteppingDistance * (step - 1)
        val greenStep = greenSteppingDistance * (step - 1)
        val blueStep = blueSteppingDistance * (step - 1)

        val ir = 255 * (start.red + redStep)
        val ig = 255 * (start.green + greenStep)
        val ib = 255 * (start.blue + blueStep)

        return Color(ir.toInt(), ig.toInt(), ib.toInt())
    }
}