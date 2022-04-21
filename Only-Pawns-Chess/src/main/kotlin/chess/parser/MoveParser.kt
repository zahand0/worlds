package chess.parser

object MoveParser {
    private val correctMovePattern = Regex("[a-h][1-8][a-h][1-8]")

    fun isCorrectMove(move: String): Boolean {
        return move.matches(correctMovePattern)
    }

    fun getCoordinates(move: String): List<Int> {
        return listOf(
            transformLetterToDigit(move[0]),    // x start
            move[1].digitToInt() - 1,           // y start
            transformLetterToDigit(move[2]),    // x end
            move[3].digitToInt() - 1            // y end
        )
    }

    fun getMove(
        xStart: Int,
        yStart: Int,
        xEnd: Int,
        yEnd: Int
    ): String {
        return transformDigitToLetter(xStart).toString() + // x start
                (yStart + 1).toString() +                  // y start
                transformDigitToLetter(xEnd) +             // x end
                (yEnd + 1).toString()                      // y end
    }

    private fun transformLetterToDigit(letter: Char): Int {
        return letter - 'a'
    }

    private fun transformDigitToLetter(num: Int): Char {
        return 'a' + num
    }
}