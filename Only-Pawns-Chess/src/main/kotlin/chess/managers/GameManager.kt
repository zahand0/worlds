package chess.managers

import chess.parser.MoveParser
import chess.entities.Player
import chess.entities.Result

object GameManager {
    private var winner: String? = null
    private val moveList = mutableListOf<String>()
    private var forceExit = false
    lateinit var currentMove: String

    private object Const {
        const val gameName = "Pawns-Only Chess"
        const val farewellLine = "Bye!"
        const val winnerLine = "%s Wins!"
        const val stalemate = "Stalemate!"
        const val firstPlayerNameInput = "First Player's name:"
        const val secondPlayerNameInput = "Second Player's name:"
        const val exitLine = "exit"
    }

    fun startGame() {
        BoardManager.setUpBoard()
        println(Const.gameName)
        // getting players names
        setUpPlayers()
        // show board
        BoardManager.printBoard()
        // game loop
        while (!isGameOver() && !forceExit) {
            nextTurn()
        }
        // print result
        if (winner != null) {
            println(Const.winnerLine.format(winner))
        } else {
            if (!forceExit) println(Const.stalemate)
        }
        // farewell
        println(Const.farewellLine)
    }

    private fun isGameOver(): Boolean {
        // pawn on last row or there's no more opposite color pawns
        if (BoardManager.isPawnPromote() || BoardManager.isNoOppositePawns()) {
            // return value back
            BoardManager.whiteTurn = !BoardManager.whiteTurn
            //set winner
            winner = BoardManager.currentColorTurn.name
            return true
        }
        // there's no more moves
        if (BoardManager.isNoMoreMove()) return true
        return false
    }

    private fun nextTurn() {
        /**
         * Getting move, checking move possibility, making move
         */
        // print whose move
        (if (BoardManager.whiteTurn) Player.firstPlayer else Player.secondPlayer)?.getTurn()
        // getting move and checking force exit
        if (!getMove()) return
        // correct move spelling
        if (MoveParser.isCorrectMove(currentMove)) {
            // get coordinates in list
            val (x0, y0, x1, y1) = MoveParser.getCoordinates(currentMove)
            // get result
            val result = BoardManager.isMovePossible(x0, y0, x1, y1)
            // move possible
            if (result.possible) {
                BoardManager.moveFigure(x0, y0, x1, y1)
                moveList.add(currentMove)
                BoardManager.whiteTurn = !BoardManager.whiteTurn
                BoardManager.printBoard()
            } else {
                // move impossible
                println(result.error)
            }
        } else {
            // incorrect move spelling
            println(Result.defaultWrongResult().error)
        }
    }

    private fun getMove(): Boolean {
        /**
         * Getting nextMove and check force exit call
         */
        // scan next turn
        currentMove = readln()
        // force exit
        forceExit = currentMove.trimIndent() == Const.exitLine
        return !forceExit
    }

    private fun setUpPlayers() {
        /**
         * Get players names
         */
        println(Const.firstPlayerNameInput)
        Player.setFirstPlayer(readln())
        println(Const.secondPlayerNameInput)
        Player.setSecondPlayer(readln())
    }

    fun getLastMove(): String {
        /**
         * get previous move or "" if now is first move
         */
        return if (moveList.isNotEmpty()) moveList.last() else ""
    }
}