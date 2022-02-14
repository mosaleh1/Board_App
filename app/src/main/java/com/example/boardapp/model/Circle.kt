package com.example.boardapp.model

import android.graphics.Paint
import android.graphics.Rect

data class Circle(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float,
    val paintC: Paint
) : Shape(paintC) {

    constructor(rect: Rect, paint: Paint) : this(
        rect.left.toFloat(), rect.top.toFloat(), rect.right.toFloat(), rect.bottom.toFloat(), paint
    )
}