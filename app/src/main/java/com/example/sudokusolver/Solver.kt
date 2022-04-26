package com.example.sudokusolver

import kotlin.collections.ArrayList

class Solver(
    var selectedRow: Int = -1,
    var selectedColumn: Int = -1,
    private var emptyBoxIndex: ArrayList<ArrayList<Any>> = ArrayList(),
    private var board: Array<Array<Int>> = Array(9) { Array(9) { 0 } }
) {
    fun getEmptyBoxIndexes() {
        for (r in 0 until 9) {
            for (c in 0 until 9) {
                if (this.board[r][c] == 0) {  //adds  new point with r and c if that spot is empty
                    this.emptyBoxIndex.add(ArrayList())
                    this.emptyBoxIndex[this.emptyBoxIndex.size - 1].add(r)
                    this.emptyBoxIndex[this.emptyBoxIndex.size - 1].add(c)
                }
            }
        }
    }

    fun getEmptyBoxIndex(): ArrayList<ArrayList<Any>> {
        return this.emptyBoxIndex
    }

    private fun check(row: Int, col: Int): Boolean {
        if (this.board[row][col] > 0) {
            for (i in 0 until 9) {
                if (this.board[i][col] == this.board[row][col] && row != i) return false    //horizontal check
                if (this.board[row][i] == this.board[row][col] && col != i) return false    //vertical check
            }
            val boxRow = row / 3
            val boxCol = col / 3
            for (r in boxRow * 3 until boxRow * 3 + 3) { //box check
                for (c in boxCol * 3 until boxCol * 3 + 3) {
                    if (this.board[r][c] == this.board[row][col] && row != r && col != c) return false
                }
            }
        }
        return true
    }

    fun solve(display: SudokuBoard): Boolean {
        var row = -1
        var col = -1

        loop@ for (r in 0 until 9) {    //find first empty cell
            for (c in 0 until 9) {
                if (this.board[r][c] == 0) {
                    row = r
                    col = c
                    break@loop
                }
            }
        }
        if (row == -1 || col == -1) return true  //if no empty cell then exit
        for (i in 1..9) {
            this.board[row][col] = i
            display.invalidate()
            if (check(row, col) && solve(display)) return true
            this.board[row][col] = 0
        }
        return false
    }


    fun resetBoard() {
        this.board = Array(9) { Array(9) { 0 } }
        /*test board
        this.board= arrayOf(arrayOf(0,0,0,7,0,0,0,0,0),
            arrayOf(1,0,0,0,0,0,0,0,0),
            arrayOf(0,0,0,4,3,0,2,0,0),
            arrayOf(0,0,0,0,0,0,0,0,6),
            arrayOf(0,0,0,5,0,9,0,0,0),
            arrayOf(0,0,0,0,0,0,4,1,8),
            arrayOf(0,0,0,0,8,7,0,0,0),
            arrayOf(0,0,2,0,0,0,0,5,0),
            arrayOf(0,4,0,0,0,0,3,0,0))*/
        this.emptyBoxIndex = ArrayList()
    }

    fun getBoard(): Array<Array<Int>> {
        return this.board
    }

    fun setNumberPos(num: Int) {
        if (this.selectedRow == -1 || this.selectedRow == -1) return
        if (this.board[this.selectedRow - 1][this.selectedColumn - 1] == num) this.board[this.selectedRow - 1][selectedColumn - 1] =
            0
        else this.board[this.selectedRow - 1][this.selectedColumn - 1] = num
    }
}