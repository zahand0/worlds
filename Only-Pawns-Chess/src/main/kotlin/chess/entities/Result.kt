package chess.entities

data class Result(val possible: Boolean, val error: String? = null) {
    companion object {
        private const val errorWrongFigure = "No %s pawn at %s"
        private const val invalidInput = "Invalid Input"

        fun wrongFigure(color: String, move: String): String {
            return errorWrongFigure.format(color, move)
        }

        fun defaultWrongResult(): Result {
            return Result(false, invalidInput)
        }
    }
}