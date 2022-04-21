package chess.managers

import chess.entities.Color
import chess.parser.MoveParser
import chess.entities.Result
import chess.figures.Pawn
import kotlin.math.abs

object BoardManager {
    private const val size = 8
    private val figuresList: MutableList<MutableList<Pawn?>> =
        MutableList(size) { MutableList(size) { null } }
    var whiteTurn = true
    val currentColorTurn: Color
        get() {
            return if (whiteTurn) Color.White else Color.Black
        }

    private object Draw {
        const val lineSeparator = "  +---+---+---+---+---+---+---+---+"
        const val abscissa = "    a   b   c   d   e   f   g   h"
        const val figuresSeparator = " | "
        const val figuresPrefix = "%d | "
        const val figuresPostfix = " |"
    }

    fun setUpBoard() {
        figuresList.map { it.map { null } }
        figuresList[1] = MutableList(size) { Pawn(Color.White) } // white pawns
        figuresList[6] = MutableList(size) { Pawn(Color.Black) } // black pawns
    }

    fun moveFigure(
        xStart: Int,
        yStart: Int,
        xEnd: Int,
        yEnd: Int
    ) {
        figuresList[yEnd][xEnd] = figuresList[yStart][xStart]
        figuresList[yStart][xStart] = null
        // en passant
        if (isEnPassant(xStart, yStart, xEnd, yEnd)) {
            removeFigure(xEnd, yStart)
        }
    }

    private fun removeFigure(x: Int, y: Int) {
        figuresList[y][x] = null
    }

    fun isMovePossible(
        xStart: Int,
        yStart: Int,
        xEnd: Int,
        yEnd: Int
    ): Result {
        return when {
            // moving wrong figure or there's no figure
            !isRightFigureOnSquare(xStart, yStart) -> {
                Result(
                    possible = false,
                    error = Result.wrongFigure(color = currentColorTurn.name, move = GameManager.currentMove)
                )
            }
            // moving wrong direction
            !isRightPawnMoveDirection(yStart, yEnd) -> Result.defaultWrongResult()
            // not moving and capturing
            !isPawnMoving(xStart, yStart, xEnd, yEnd) &&
                    !isPawnCapturing(xStart, yStart, xEnd, yEnd) -> Result.defaultWrongResult()
            else -> Result(true)
        }

    }

    private fun isRightFigureOnSquare(x: Int, y: Int): Boolean {
        return whiteTurn && figuresList[y][x]?.color == Color.White ||
                !whiteTurn && figuresList[y][x]?.color == Color.Black
    }

    // pawn can only move forward
    private fun isRightPawnMoveDirection(yStart: Int, yEnd: Int): Boolean {
        return whiteTurn && yEnd > yStart || !whiteTurn && yEnd < yStart
    }

    private fun isPawnMoving(
        xStart: Int,
        yStart: Int,
        xEnd: Int,
        yEnd: Int
    ): Boolean {
        // on square another figure
        if (figuresList[yEnd][xEnd] != null) return false
        // if not on same abscissa check en passant
        if (xStart != xEnd) return isEnPassant(xStart, yStart, xEnd, yEnd)
        // difference between ordinates
        return when (yEnd - yStart) {
            // move on 1 square forward
            1, -1 -> true
            // next square are empty and pawn didn't move before
            2 -> figuresList[yEnd - 1][xEnd] == null && yStart == 1
            -2 -> figuresList[yEnd + 1][xEnd] == null && yStart == 6
            else -> false
        }
    }

    private fun isPawnCapturing(
        xStart: Int,
        yStart: Int,
        xEnd: Int,
        yEnd: Int
    ): Boolean {
        // pawn can attack only 1 square ahead
        if (abs(xEnd - xStart) != 1 || abs(yEnd - yStart) != 1) return false
        return figuresList[yEnd][xEnd]?.color == Color.Black && whiteTurn ||
                figuresList[yEnd][xEnd]?.color == Color.White && !whiteTurn
    }

    private fun isEnPassant(
        xStart: Int,
        yStart: Int,
        xEnd: Int,
        yEnd: Int
    ): Boolean {
        // not diagonal 1 square length move
        if (abs(xEnd - xStart) != 1 || abs(yEnd - yStart) != 1) return false
        // previous move was first turn of capturing pawn
        if (MoveParser.getMove(
                xStart = xEnd,
                yStart = 2 * yEnd - yStart,
                xEnd = xEnd,
                yEnd = yStart
            ) != GameManager.getLastMove()
        ) return false
        // opposite pawn on right place
        return figuresList[yStart][xEnd]?.color == Color.Black && whiteTurn ||
                figuresList[yStart][xEnd]?.color == Color.White && !whiteTurn
    }

    fun isPawnPromote(): Boolean {
        return (figuresList[0].any { it?.color == Color.Black } ||
                figuresList[size - 1].any { it?.color == Color.White })
    }

    fun isNoOppositePawns(): Boolean {
        var whiteExist = false
        var blackExist = false
        for (row in figuresList) {
            if (row.any { it?.color == Color.Black }) blackExist = true
            if (row.any { it?.color == Color.White }) whiteExist = true
        }
        return !whiteExist || !blackExist
    }

    fun isNoMoreMove(): Boolean {
        for (y in 0 until size) {
            for (x in 0 until size) {
                if (figuresList[y][x]?.color == currentColorTurn && canPawnMove(x, y)) return false
            }
        }
        return true
    }

    private fun canPawnMove(x: Int, y: Int): Boolean {
        val sign = if (whiteTurn) 1 else -1
        // check borders
        if (y + sign in 0 until size) {
            if (x > 0 && isMovePossible(x, y, x - 1, y + sign).possible) return true
            if (isMovePossible(x, y, x, y + sign).possible) return true
            if (x + 1 < size && isMovePossible(x, y, x + 1, y + sign).possible) return true
        }
        return false
    }

    fun printBoard() {
        println(Draw.lineSeparator)
        for (y in (size - 1) downTo 0) {
            println(
                figuresList[y].joinToString(
                    separator = Draw.figuresSeparator,
                    prefix = Draw.figuresPrefix.format(y + 1),
                    postfix = Draw.figuresPostfix
                ) { it?.color?.symbol ?: " " })
            println(Draw.lineSeparator)
        }
        println(Draw.abscissa)
        println()
    }
}