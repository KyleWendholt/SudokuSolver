package com.example.sudokusolver

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class SudokuBoard1(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var boardColor:Int = 0
    private var boardColorPaint = Paint()
    private var cellSize:Int = 0

    init {
        val arr:TypedArray = context!!.theme.obtainStyledAttributes(attrs,R.styleable.SudokuBoard,0,0)
        try{

        }finally {
            arr.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val dimension = min(this.measuredWidth,this.measuredHeight)
        cellSize= dimension/9

        setMeasuredDimension(dimension,dimension)
    }

    override fun onDraw(canvas: Canvas?) {
        boardColorPaint.style = Paint.Style.STROKE
        boardColorPaint.strokeWidth = 16F
        boardColorPaint.color = boardColor
        boardColorPaint.isAntiAlias = true

        canvas?.drawRect(0F, 0F, width.toFloat(), height.toFloat(),boardColorPaint)
        if (canvas != null) {
            drawBoard(canvas)
        }
    }
    private fun drawBoard(canvas:Canvas){
        for (i in 0..9){
            if (i%3==0) drawThickLine()
            else drawThinLine()
            canvas.drawLine((cellSize*i).toFloat(), 0F, (cellSize*i).toFloat(),
                width.toFloat(),boardColorPaint)
        }
        for (i in 0..9){
            if (i%3==0)drawThickLine()
            else drawThinLine()
            canvas.drawLine(0F, (cellSize*i).toFloat(), width.toFloat(),
                (cellSize*i).toFloat(),boardColorPaint)
        }
    }

    private fun drawThickLine(){
        boardColorPaint.style = Paint.Style.STROKE
        boardColorPaint.strokeWidth = 10F
        boardColorPaint.color = boardColor
        boardColorPaint.isAntiAlias = true
    }
    private fun drawThinLine(){
        boardColorPaint.style = Paint.Style.STROKE
        boardColorPaint.strokeWidth = 4F
        boardColorPaint.color = boardColor
        boardColorPaint.isAntiAlias = true
    }

}