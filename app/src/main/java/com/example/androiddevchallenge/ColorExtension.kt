package com.example.androiddevchallenge

import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.compose.ui.graphics.Color

fun Color.parse(colorString: String): Color {
    return Color.Green
}

fun Color.alpha(@FloatRange(from=0.0, to=1.0) alpha: Float): Color {
    return Color(this.red, this.green, this.blue, alpha = alpha)
}