package com.example.boardapp.model

import android.graphics.Paint

data class Rectangle(
    val left : Float,
    val top : Float,
    val right : Float,
    val bottom : Float,
    val rectPaint:Paint
    ):Shape(rectPaint)