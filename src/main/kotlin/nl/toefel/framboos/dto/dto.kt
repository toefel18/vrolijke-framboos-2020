package nl.toefel.framboos.dto


data class Scoreboard(
    val currentRound: CurrentRound,
    val endTime: String,
    val players: List<Player>
)

data class CurrentRound(
    val expression: Expression,
    val lastRoundWinner: String?,
    val number: Long
)

data class Player(
    val lastResult: String,
    val lastRound: Long,
    val lowerBound: Long,
    val name: String,
    val score: Long,
    val scoreIncrement: Long,
    val upperBound: Long
)

data class Expression(
    val operands: List<Long>,
    val operators: List<String>,
    val value: Long
)

enum class Result {
    CORRECT,
    TOO_LOW,
    TOO_HIGH,
    INCORRECT_ROUND,
    INCORRECT_LENGTH,
    OUT_OF_BOUNDS,
    DOES_NOT_COMPUTE,
    GAME_ENDED
}

data class SolutionResponse(
    val currentRound: Long,
    val problem: List<Long>,
    val result: Result
)

data class SolutionRequest(
    val operators: List<String>,
    val round: Long
)