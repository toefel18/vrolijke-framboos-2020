package nl.toefel.framboos

object Bruteforcer {

    val options = listOf("+", "-", "*", "/")


    @JvmStatic
    fun computeCombinations(size: Int): List<List<String>> {
        if (size <= 0) return listOf()
        val x = options.flatMap { it.repeat(size).toCharArray().toList() }
        return computeCombinations(x)
            .map {
                if (it.size > size) {
                    it.subList(0, size)
                } else if (it.size == size) {
                    it
                } else {
                    null
                }
            }
            .filterNotNull()
            .map { option -> option.map { it.toString() } }
            .distinct()
    }

    /**
     * Computes all the possible combinations of the input. It can contains duplicates if input contains the same
     * char twice. use [computeUniqueCombinations] to dedupe.
     *
     * See unit tests for example cases.
     */
    @JvmStatic
    fun computeCombinations(input: List<Char>): List<List<Char>> {
        if (input.isEmpty()) return listOf()
        if (input.size == 1) return listOf(input)
        val combinationsExcludingFirst: List<List<Char>> = computeCombinations(input.drop(1))
        val combinationsIncludingFirst: List<List<Char>> = combinationsExcludingFirst.map { it.plus(input.first()) }
        val firstAlone: List<List<Char>> = listOf(listOf(input.first()))
        return listOf(firstAlone, combinationsIncludingFirst, combinationsExcludingFirst).flatten()
    }
}