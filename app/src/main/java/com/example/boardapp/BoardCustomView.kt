package com.example.boardapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.boardapp.model.*
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "BoardCustomView"

class BoardCustomView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val pathList = ArrayList<Shape>()

    private var action: BoardActions = BoardActions.ActionPen

    var startX: Float = 0f
    var startY: Float = 0f
    var endX: Float = 0f
    var endY: Float = 0f

    fun changeState(actionParam: BoardActions) {
        action = actionParam
    }


    //what color
    //state of color
    //what to draw
    //state of current Selected


    //path
    var path: Path = Path()
    private val paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 10f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }
    var isActionDown: Boolean = false
    var isOneClick = true
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (action is BoardActions.ActionPen)
                    path.moveTo(x, y)
                startX = x
                startY = y
                isActionDown = true
                Log.d(TAG, "onTouchEvent:D x:$x y:$y ")
            }
            MotionEvent.ACTION_UP -> {
                Log.d(TAG, "onTouchEvent:U x:$x y:$y ")
                isActionDown = false
                isOneClick = true
            }
            MotionEvent.ACTION_MOVE -> {
                endX = x
                endY = y
                isOneClick = false
                if (action is BoardActions.ActionPen)
                    path.lineTo(x, y)
                Log.d(TAG, "onTouchEvent: Action Move")
            }
            else -> {

            }
        }
        if (isOneClick) {
            if (action is BoardActions.ActionRectangle && !isActionDown) {
                synchronized(pathList) {
                    pathList.add(Rectangle(startX, startY, endX, endY, Paint(paint)))
                }
            } else if (action is BoardActions.ActionPen && !isActionDown) {
                synchronized(pathList) {
                    pathList.add(Prush(path, Paint(paint)))
                }
            } else if (action is BoardActions.ActionArrow && !isActionDown) {
                synchronized(pathList) {
                    pathList.add(Arrow(startX, startY, endX, endY, Paint(paint)))
                }

            } else if (action is BoardActions.ActionArrow && !isActionDown) {
                synchronized(pathList) {
                    val rect = Rect(startX.toInt(), startY.toInt(), endX.toInt(), endY.toInt())
                    val radius = ((startX - endX) + (startY - endY))
                    pathList.add(
                        Circle(
                            rect.centerX().toFloat(),
                            rect.centerY().toFloat(),
                            radius.toInt(),
                            Paint(paint)
                        )
                    )
                }
            }
        }
        invalidate()
        // postInvalidate(startX.toInt(),startY.toInt(),endX.toInt(),endY.toInt())
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()

        synchronized(pathList) {
            for (shape in pathList) {
                when (shape) {
                    is Rectangle -> {
                        drawRect(canvas, shape)
                    }
                    is Circle -> {
                        drawCircle(canvas, shape)
                    }
                    is Prush -> {
                        drawPath(canvas, shape)
                    }
                    is Arrow -> {
                        drawArrow(canvas, shape)
                    }
                }
                canvas.save()
            }
        }
        when (action) {
            BoardActions.ActionPen -> {
                drawPath(canvas)
            }
            BoardActions.ActionArrow -> {
                drawArrow(canvas)
            }
            BoardActions.ActionCircle -> {
                drawCircle(canvas)
                canvas.save()
            }
            BoardActions.ActionRectangle -> {
                drawRect(canvas)
            }
        }
    }


    private fun drawArrow(canvas: Canvas, shape: Arrow) {
        canvas.drawLine(shape.startX, shape.startY, shape.stopX, shape.stopY, shape.arrowPaint)
    }

    private fun drawPath(canvas: Canvas, shape: Prush) {
        canvas.drawPath(path, shape.paintParam)
        canvas.save()
    }

    private fun drawPath(canvas: Canvas) {
        canvas.drawPath(path, paint)
        canvas.save()
    }

    private fun drawRect(canvas: Canvas, shape: Rectangle) {
        canvas.drawRect(shape.left, shape.top, shape.right, shape.bottom, shape.rectPaint)
    }

    private fun drawRect(canvas: Canvas) {
        canvas.drawRect(startX, startY, endX, endY, paint)
    }

    private fun drawCircle(canvas: Canvas, shape: Circle) {
        canvas.drawCircle(
            shape.cx
            ,shape.cy,
            shape.radius.toFloat(),
            shape.paintC
        )
        canvas.save()
    }

    private fun drawCircle(canvas: Canvas) {
        //val radius = ((startX - endX) + (startY - endY))
        val radius = (startX - endX) - (startY - endY)
        val rect = Rect(startX.toInt(), startY.toInt(), endX.toInt(), endY.toInt())
        canvas.drawCircle(rect.centerX().toFloat(), rect.centerY().toFloat(), radius, paint)
    }

    private fun drawArrow(canvas: Canvas) {
        canvas.drawLine(startX, startY, endX, endY, paint)
    }

    fun changeColor(color: Int) {
        paint.color = color
    }
}