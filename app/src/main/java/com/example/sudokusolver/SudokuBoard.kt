package com.example.sudokusolver

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.ceil
import kotlin.math.min

class SudokuBoard(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var boardColor = 0
    private var cellFillColor = 0
    private var cellHighlightColor = 0
    private val boardColorPaint = Paint()
    private val cellFillColorPaint = Paint()
    private val cellHighlightColorPaint = Paint()
    private var cellSize = 0
    private val solver = Solver()
    private var letterColor = 0
    private var letterColorSolve = 0
    private val letterPaint = Paint()
    private val letterPaintBounds = Rect()

    override fun onMeasure(width: Int, height: Int) {
        super.onMeasure(width, height)
        val dimension = min(this.measuredWidth, this.measuredHeight)
        cellSize = dimension / 9
        setMeasuredDimension(dimension, dimension)
    }

    override fun onDraw(canvas: Canvas) {
        boardColorPaint.style = Paint.Style.STROKE
        boardColorPaint.strokeWidth = 16f
        boardColorPaint.color = boardColor
        boardColorPaint.isAntiAlias = true

        cellFillColorPaint.style = Paint.Style.FILL
        cellFillColorPaint.color = cellFillColor
        cellFillColorPaint.isAntiAlias = true


        cellHighlightColorPaint.style = Paint.Style.FILL
        cellHighlightColorPaint.color = cellHighlightColor
        cellHighlightColorPaint.isAntiAlias = true

        letterPaint.style = Paint.Style.FILL
        letterPaint.isAntiAlias = true
        letterPaint.color = letterColor

        colorCell(canvas, solver.selectedRow, solver.selectedColumn)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), boardColorPaint)
        drawBoard(canvas)
        drawNumbers(canvas)
    }

    private fun drawBoard(canvas: Canvas) {
        for (i in 0..9) {
            if (i % 3 == 0) {
                drawThickLine()
            } else {
                drawThinLine()
            }
            canvas.drawLine(
                (cellSize * i).toFloat(),
                0f,
                (cellSize * i).toFloat(),
                width.toFloat(),
                boardColorPaint
            )
        }
        for (i in 0..9) {
            if (i % 3 == 0) drawThickLine() else drawThinLine()
            canvas.drawLine(
                0f,
                (cellSize * i).toFloat(),
                width.toFloat(),
                (cellSize * i).toFloat(),
                boardColorPaint
            )
        }
    }

    private fun drawThinLine() {
        boardColorPaint.style = Paint.Style.STROKE
        boardColorPaint.strokeWidth = 4f
        boardColorPaint.color = boardColor
    }

    private fun drawThickLine() {
        boardColorPaint.style = Paint.Style.STROKE
        boardColorPaint.strokeWidth = 10f
        boardColorPaint.color = boardColor
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val isValid: Boolean

        val x = event.x
        val y = event.y

        val action = event.action

        isValid = action == MotionEvent.ACTION_DOWN

        if (isValid) {
            solver.selectedRow = ceil(y / cellSize).toInt()
            solver.selectedColumn = ceil(x / cellSize).toInt()
        }

        return isValid
    }

    private fun colorCell(canvas: Canvas, row: Int, column: Int) {
        if (solver.selectedColumn != -1 && solver.selectedRow != -1) {
            canvas.drawRect(
                ((column - 1) * cellSize).toFloat(),
                0F,
                (column * cellSize).toFloat(),
                (cellSize * 9).toFloat(),
                cellHighlightColorPaint
            )
            canvas.drawRect(
                0f,
                ((row - 1) * cellSize).toFloat(),
                (cellSize * 9).toFloat(),
                (row * cellSize).toFloat(),
                cellHighlightColorPaint
            )
            canvas.drawRect(
                ((column - 1) * cellSize).toFloat(),
                ((row - 1) * cellSize).toFloat(),
                (column * cellSize).toFloat(),
                (row * cellSize).toFloat(),
                cellHighlightColorPaint
            )
        }

        invalidate()
    }

    private fun drawNumbers(canvas: Canvas) {
        letterPaint.textSize = cellSize.toFloat()

        for (r in 0 until 9) {
            for (c in 0 until 9) {
                if (solver.getBoard()[r][c] != 0) {
                    drawNum(r, c, canvas)

                }
            }
        }
        letterPaint.color = letterColorSolve
        for (letter in solver.getEmptyBoxIndex()) {
            val r: Int = letter[0] as Int
            val c: Int = letter[1] as Int
            drawNum(r, c, canvas)
        }
    }

    private fun drawNum(r: Int, c: Int, canvas: Canvas) {
        val text = solver.getBoard()[r][c].toString()

        letterPaint.getTextBounds(text, 0, text.length, letterPaintBounds)
        val width: Float = letterPaint.measureText(text)
        val height: Float = letterPaintBounds.height().toFloat()

        canvas.drawText(
            text,
            (c * cellSize) + (cellSize - width) / 2,
            (r * cellSize + cellSize) - (cellSize - height) / 2,
            letterPaint
        )
    }

    fun getSolver(): Solver {
        return this.solver
    }

    init {
        val arr = context.theme.obtainStyledAttributes(attrs, R.styleable.SudokuBoard, 0, 0)
        try {
            boardColor = arr.getInteger(R.styleable.SudokuBoard_boardColor, 0)
            cellFillColor = arr.getInteger(R.styleable.SudokuBoard_cellFillColor, 0)
            cellHighlightColor = arr.getInteger(R.styleable.SudokuBoard_cellsHighlightColor, 0)
            letterColor = arr.getInteger(R.styleable.SudokuBoard_letterColor, 0)
            letterColorSolve = arr.getInteger(R.styleable.SudokuBoard_letterColorSolve, 0)
        } finally {
            arr.recycle()
        }
    }
}