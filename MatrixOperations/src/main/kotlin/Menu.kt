object Menu {

    private const val wrongInputLine = "The operation cannot be performed."
    private const val scanChoiceLine = "Your choice: "
    private val listOfAction = listOf(
        "Add matrices",
        "Multiply matrix by a constant",
        "Multiply matrices",
        "Exit"
    )
    private var exit = false

    fun menuLoop() {
        while (!exit) {
            showMenu()
            chooseAction()
        }
    }

    private fun showMenu() {
        for (index in 0 until listOfAction.lastIndex) {
            println("${index + 1}. ${listOfAction[index]}")
        }
        println("0. ${listOfAction.last()}")
    }

    private fun chooseAction() {
        print(scanChoiceLine)
        try {
            when (readln().toIntOrNull()) {
                1 -> Matrix.sumMatrices()
                2 -> Matrix.multiplyByConstant()
                3 -> Matrix.multiplyMatrices()
                0 -> exit()
                else -> wrongInput()
            }
        }
        catch (e: IllegalStateException) {
            wrongInput()
        }
        catch (e: Exception) {
            wrongInput()
        }
        println()
    }

    private fun wrongInput() {
        println(wrongInputLine)
    }

    private fun exit(): Boolean {
        exit = true
        return true
    }

}