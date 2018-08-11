package com.isaaclyman.humanbingo

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.GridLayout
import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import java.util.*
import kotlin.math.sqrt

class GameCell(cell: RelativeLayout, val textView: TextView, var isChecked: Boolean, var row: Int, var col: Int, val onChangeListener: () -> Unit) {
    init {
        cell.setOnClickListener(View.OnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                cell.setBackgroundColor(0xFF007849.toInt())
                textView.setTextColor(0xFFFFFFFF.toInt())
            } else {
                cell.setBackgroundColor(0xFFFFFFFF.toInt())
                textView.setTextColor(0xFF000000.toInt())
                cell.setBackgroundResource(R.drawable.bordered_rectangle)
            }
            onChangeListener()
        })
    }
}

class GameBoard {
    companion object {
        val codeSeparator = "-"
    }

    private val context: Context
    private val board: GridLayout
    private val announcer: TextView
    private val peopleSquares = PeopleSquares()
    private var peopleIndexes: List<Int>? = null
    private val cells = mutableListOf<GameCell>()

    constructor(context: Context, board: GridLayout, announcer: TextView, mode: GameMode?, code: String?) {
        this.context = context
        this.board = board
        this.announcer = announcer

        if (mode != null) {
            // Create new game
            newBoard(mode.value)
        } else if (code != null) {
            // Create predefined game
            val people = getPeopleByCode(code.split(codeSeparator).map { it.toInt() })
            createBoard(context, board, sqrt(people.size.toDouble()).toInt(), people)
        }
    }

    fun newBoard(size: Int) {
        val squares = size * size
        val people = getRandomPeople(squares)
        createBoard(context, board, size, people)
    }

    fun getCode(): String {
        return peopleIndexes?.joinToString(codeSeparator) ?: ""
    }

    private fun ClosedRange<Int>.random() =
            Random().nextInt((endInclusive + 1) - start) +  start

    private fun createBoard(context: Context, board: GridLayout, size: Int, people: List<String>) {
        destroyBoard()
        board.columnCount = size
        board.rowCount = size
        var iterator = 0

        for(col in 1..size) {
            for (row in 1..size) {
                val cell = RelativeLayout(context)
                val cellSpec = { GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f) }
                val params = GridLayout.LayoutParams(cellSpec(), cellSpec())
                params.width = 0
                cell.layoutParams = params
                cell.setBackgroundResource(R.drawable.bordered_rectangle)
                cell.gravity = Gravity.CENTER
                cell.setPadding(10, 4, 10, 4)

                val text = TextView(context)
                text.text = people[iterator++]
                text.maxLines = 5
                text.setSingleLine(false)
                text.gravity = Gravity.CENTER
                text.setTextColor(0xFF000000.toInt())

                cell.addView(text)
                board.addView(cell)
                cells.add(GameCell(cell, text, false, row, col) { checkWinCondition(size) })
            }
        }
    }

    private fun getRandomPeople(num: Int): List<String> {
        var validIndexes = (0..peopleSquares.squares.lastIndex).toMutableList()
        var chosenIndexes = mutableListOf<Int>()
        var randomPeople = mutableListOf<String>()
        for (i in 1..num) {
            val validIndexIndex = (0..validIndexes.lastIndex).random()
            val peopleIndex = validIndexes[validIndexIndex]
            chosenIndexes.add(peopleIndex)
            val person = peopleSquares.squares[peopleIndex]
            randomPeople.add(person)
            validIndexes.remove(peopleIndex)
        }
        peopleIndexes = chosenIndexes.toList()
        return randomPeople.toList()
    }

    private fun getPeopleByCode(indexes: List<Int>): List<String> {
        peopleIndexes = indexes
        return indexes.map { peopleSquares.squares[it] }.shuffled()
    }

    private fun destroyBoard() {
        board.removeAllViews()
        cells.clear()
        announcer.text = ""
    }

    private fun checkWinCondition(size: Int) {
        val blackout = cells.all { it.isChecked }
        if (blackout) {
            setWin(false, blackout)
            return
        }

        val rowCellsChecked = Array(size) {0}
        var colCellsChecked = Array(size) {0}
        var firstDiagChecked = 0
        var secondDiagChecked = 0

        for(cell in cells.filter { it.isChecked }) {
            rowCellsChecked[cell.row - 1]++
            colCellsChecked[cell.col - 1]++

            if (cell.row == cell.col) {
                firstDiagChecked++
            }

            if (cell.row + cell.col == size + 1) {
                secondDiagChecked++
            }
        }

        val bingo = arrayOf(*rowCellsChecked, *colCellsChecked, firstDiagChecked, secondDiagChecked).contains(size)
        setWin(bingo, blackout)
    }

    private fun setWin(bingo: Boolean, blackout: Boolean) {
        if (blackout) {
            announcer.text = "BLACKOUT!"
        } else if (bingo) {
            announcer.text = "BINGO!"
        } else {
            announcer.text = ""
        }
    }
}