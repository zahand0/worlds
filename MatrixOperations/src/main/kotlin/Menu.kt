object Menu {

    private const val wrongInputLine = "The operation cannot be performed."
    private const val scanChoiceLine = "Your choice: "
    private val listOfAction = listOf(
        "Add matrices",
        "Multiply matrix by a constant",
        "Multiply matrices",
        "Transpose matrix",
        "Calculate a determinant",
        "Exit"
    )
    private val listOfTransposeAction = listOf(
        "Main diagonal",
        "Side diagonal",
        "Vertical line",
        "Horizontal line",
    )
    private var exit = false

    fun menuLoop() {
        /**
         * Main program loop with action choosing
         */
        while (!exit) {
            showMenu()
            chooseAction()
        }
    }

    private fun showMenu() {
        /**
         * Shows menu of actions
         */
        for (index in 0 until listOfAction.lastIndex) {
            println("${index + 1}. ${listOfAction[index]}")
        }
        println("0. ${listOfAction.last()}")
    }

    private fun showTransposeActions() {
        /**
         * Shows menu of Transpose matrix actions
         */
        for (index in listOfTransposeAction.indices) {
            println("${index + 1}. ${listOfTransposeAction[index]}")
        }
    }

    private fun chooseAction() {
        /**
         * Getting number of action and execute relevant function
         */
        print(scanChoiceLine)
        try {
            when (readln().toIntOrNull()) {
                1 -> Matrix.sumMatrices()
                2 -> Matrix.multiplyByConstant()
                3 -> Matrix.multiplyMatrices()
                4 -> transposeMatrix()
                5 -> Matrix.matrixDeterminant()
                0 -> exit()
                else -> wrongInput()
            }
        } catch (e: IllegalStateException) {
            wrongInput()
        } catch (e: Exception) {
            wrongInput()
        }
        println()
    }

    private fun transposeMatrix() {
        /**
         * Getting number of transpose matrix action and execute relevant function
         */
        println()
        showTransposeActions()
        print(scanChoiceLine)
        when (readln().toIntOrNull()) {
            1 -> Matrix.transposeMatrix()
            2 -> Matrix.transposeMatrixSideDiagonal()
            3 -> Matrix.transposeMatrixVerticalLine()
            4 -> Matrix.transposeMatrixHorizontalLine()
            else -> wrongInput()
        }
    }

    private fun wrongInput() {
        /**
         * Printing line with error message
         */
        println(wrongInputLine)
    }

    private fun exit(): Boolean {
        /**
         * Closing program
         */
        exit = true
        return true
    }

}