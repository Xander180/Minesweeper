package minesweeper

import kotlin.random.Random
import kotlin.system.exitProcess

class Minefield(mines: Int) {
    private var hintField = MutableList(9) { MutableList(9) { '.' } }
    private var playerField = MutableList(9) { MutableList(9) { '.' } }
    private var mineField = MutableList(9) { MutableList(9) { '.' } }
    private val numOfRows = hintField.indices
    private val numOfCols = hintField[0].indices
    private var minesFound = 0
    private val totalMines = mines
    private var hideMines = mines

    init {
        while (hideMines > 0) {
            val x = Random.nextInt(9)
            val y = Random.nextInt(9)
            if (mineField[x][y] == '.') {
                mineField[x][y] = 'X'
                hideMines--
            }
        }
    }

    fun exposedField() {
        for (i in numOfRows) {
            for (j in numOfCols) {
                if (mineField[i][j] != 'X') {
                    var tmpCoordinate = mineField[i][j]
                    val mineCount = checkMines(row = i, col = j)
                    if (tmpCoordinate == '.' && mineCount > 0) tmpCoordinate =
                        mineCount.toString().first() else tmpCoordinate += mineCount
                    hintField[i][j] = tmpCoordinate
                }
            }
        }
        printField(playerField)
    }

    private fun checkMines(row: Int, col: Int): Int {
        var count = 0

        for (i in -1..1) {
            for (j in -1..1) {
                val checkRow = row + i
                val checkCol = col + j
                if (checkRow in numOfRows && checkCol in numOfCols &&
                    mineField[checkRow][checkCol] == 'X'
                ) count++
            }
        }
        return count
    }

    private fun printField(field: MutableList<MutableList<Char>>) {
        println(" |123456789|")
        println("-|---------|")
        for (i in numOfRows) {
            print(i + 1).toString()
            print('|')
            println(field[i].joinToString("") + "|")
        }
        println("-|---------|")
        if (field == mineField || minesFound == totalMines) return
        playGame()
    }

    private fun playGame() {
        if (playerField == hintField) {
            println("Congratulations! You found all the mines!")
            exitProcess(0)
        }
        println("Set/unset mines marks or claim a cell as free:")
        val location = readln()
        val locationAsList = location.replace(" ", "").toList()
        checkCondition(location, locationAsList[0].digitToInt() - 1, locationAsList[1].digitToInt() - 1)
    }

    private fun checkCondition(location: String, x: Int, y: Int) {
        if (location.contains("mine")) mineMark(x, y)
        if (location.contains("free")) {
            if (mineField[y][x] == 'X') {
                printField(mineField)
                println("You stepped on a mine and failed!")
                exitProcess(0)
            }
            freeExplore(x, y)
            printField(playerField)
        }
    }

    private fun mineMark(x: Int, y: Int) {
        if (playerField[y][x].isDigit()) {
            println("There is a number here!")
            playGame()
        }
        if (playerField[y][x] == '*') playerField[y][x] = '.' else playerField[y][x] = '*'
        if (mineField[y][x] == 'X') minesFound++
        if (minesFound == totalMines) {
            printField(playerField)
            println("Congratulations! You found all the mines!")
            exitProcess(0)
        }
        printField(playerField)
    }

    private fun freeExplore(x: Int, y: Int) {
        if (y !in numOfRows || x !in numOfCols) return
        if (hintField[y][x].digitToIntOrNull() != null) playerField[y][x] = hintField[y][x]
        if (playerField[y][x] != '.' && playerField[y][x] != '*' || playerField[y][x] == '/') return

        playerField[y][x] = '/'
        hintField[y][x] = '/'
        mineField[y][x] = '/'

        freeExplore(x + 1, y)
        freeExplore(x - 1, y)
        freeExplore(x, y + 1)
        freeExplore(x, y - 1)
        freeExplore(x + 1, y - 1)
        freeExplore(x + 1, y + 1)
        freeExplore(x - 1, y + 1)
        freeExplore(x - 1, y - 1)
    }
}

fun main() {
    println("How many mines do you want on the field?")
    val totalMines = readln().toInt()

    val gameField = Minefield(totalMines)
    gameField.exposedField()
}