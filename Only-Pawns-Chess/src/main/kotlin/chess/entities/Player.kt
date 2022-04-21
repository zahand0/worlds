package chess.entities

class Player(private val playerName: String) {

    companion object {
        const val playerTurnLine = "%s's turn:"
        var firstPlayer: Player? = null
        var secondPlayer: Player? = null

        fun setFirstPlayer(name: String) {
            firstPlayer = Player(name)
        }

        fun setSecondPlayer(name: String) {
            secondPlayer = Player(name)
        }
    }

    fun getTurn() {
        println(playerTurnLine.format(playerName))
    }

}