package com.example.sudokusolver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.sudokusolver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var gameBoardSolver: Solver
    private lateinit var gameBoard: SudokuBoard
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameBoard = findViewById(R.id.SudokuBoard)
        gameBoardSolver = gameBoard.getSolver()

        gameBoardSolver.resetBoard()
        gameBoard.invalidate()

        binding.button1.setOnClickListener { BTNOnePress() }
        binding.button2.setOnClickListener { BTNTwoPress() }
        binding.button3.setOnClickListener { BTNThreePress() }
        binding.button4.setOnClickListener { BTNFourPress() }
        binding.button5.setOnClickListener { BTNFivePress() }
        binding.button6.setOnClickListener { BTNSixPress() }
        binding.button7.setOnClickListener { BTNSevenPress() }
        binding.button8.setOnClickListener { BTNEightPress() }
        binding.button9.setOnClickListener { BTNNinePress() }
        binding.solveBtn.setOnClickListener { solve() }


    }

    private fun BTNOnePress() {
        gameBoardSolver.setNumberPos(1)
        gameBoard.invalidate()
    }

    private fun BTNTwoPress() {
        gameBoardSolver.setNumberPos(2)
        gameBoard.invalidate()
    }

    private fun BTNThreePress() {
        gameBoardSolver.setNumberPos(3)
        gameBoard.invalidate()
    }

    private fun BTNFourPress() {
        gameBoardSolver.setNumberPos(4)
        gameBoard.invalidate()
    }

    private fun BTNFivePress() {
        gameBoardSolver.setNumberPos(5)
        gameBoard.invalidate()
    }

    private fun BTNSixPress() {
        gameBoardSolver.setNumberPos(6)
        gameBoard.invalidate()
    }

    private fun BTNSevenPress() {
        gameBoardSolver.setNumberPos(7)
        gameBoard.invalidate()
    }

    private fun BTNEightPress() {
        gameBoardSolver.setNumberPos(8)
        gameBoard.invalidate()
    }

    private fun BTNNinePress() {
        gameBoardSolver.setNumberPos(9)
        gameBoard.invalidate()
    }

    private fun solve() {
        if (binding.solveBtn.text.toString().equals(getString(R.string.solve))
        ) {
            binding.solveBtn.text = getString(R.string.clear)

            gameBoardSolver.getEmptyBoxIndexes()

            val solveBoardThread = SolveBoardThread()

            Thread(solveBoardThread).start()

            gameBoard.invalidate()

        } else {
            binding.solveBtn.text = getString(R.string.solve)

            gameBoardSolver.resetBoard()
            gameBoard.invalidate()
        }
    }

    inner class SolveBoardThread : Runnable {
        override fun run() {
            gameBoardSolver.solve(gameBoard)
        }

    }
}