package com.example.boardapp.model

import android.graphics.Paint

data class Arrow(    val startX : Float,
                     val startY : Float,
                     val stopX : Float,
                     val stopY : Float,
                     val arrowPaint: Paint
):Shape(arrowPaint)
