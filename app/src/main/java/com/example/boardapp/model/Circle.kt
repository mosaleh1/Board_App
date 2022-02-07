package com.example.boardapp.model

import android.graphics.Paint

data class Circle(
    val cx:Float,
    val cy:Float,
    val radius:Int,
    val paintC:Paint) : Shape(paintC)