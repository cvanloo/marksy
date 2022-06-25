package ch.vanloo.marksy

import androidx.compose.ui.graphics.Color

class ColorGradient(
    private val start: Color,
    end: Color,
    steppingDistance: Float,
) {
    private val redSteppingDistance = (end.red - start.red) / steppingDistance
    private val greenSteppingDistance = (end.green - start.green) / steppingDistance
    private val blueSteppingDistance = (end.blue - start.blue) / steppingDistance

    fun calculate(step: Float): Color {
        val redStep = redSteppingDistance * (step - 1)
        val greenStep = greenSteppingDistance * (step - 1)
        val blueStep = blueSteppingDistance * (step - 1)

        val ir = 255 * (start.red + redStep)
        val ig = 255 * (start.green + greenStep)
        val ib = 255 * (start.blue + blueStep)

        return Color(ir.toInt(), ig.toInt(), ib.toInt())
    }
}