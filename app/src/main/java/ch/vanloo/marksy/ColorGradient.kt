package ch.vanloo.marksy

import android.content.Context

class ColorGradient(private val context: Context) {
    fun calculate(value: Float): Int {
        return when {
            value == 6.0f -> context.getColor(R.color.mark_6_0)
            value >= 5.5f -> context.getColor(R.color.mark_5_5)
            value >= 5.0f -> context.getColor(R.color.mark_5_0)
            value >= 4.5f -> context.getColor(R.color.mark_4_5)
            value >= 4.0f -> context.getColor(R.color.mark_4_0)
            else -> context.getColor(R.color.mark_failing)
        }
    }
}