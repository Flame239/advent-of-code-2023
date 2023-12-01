fun main() {
    val input by lazy {
        readInput("Day1")
    }

    fun part1(): Int = input.sumOf { line -> line.first { it.isDigit() }.digitToInt() * 10 + line.last { it.isDigit() }.digitToInt() }

    val digitWords = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    fun part2(): Int = input.sumOf { line ->
        val firstDigit: Int
        val secondDigit: Int

        val firstDigitIdx = line.indexOfFirst { it.isDigit() }
        val firstWordDigit = digitWords.map { line.indexOf(it) }.withIndex().filter { it.value >= 0 }.minByOrNull { it.value } ?: IndexedValue(Int.MAX_VALUE, Int.MAX_VALUE)
        firstDigit = if (firstDigitIdx < firstWordDigit.value) line[firstDigitIdx].digitToInt() else firstWordDigit.index + 1

        val secondDigitIdx = line.indexOfLast { it.isDigit() }
        val secondWordDigit = digitWords.map { line.lastIndexOf(it) }.withIndex().filter { it.value >= 0 }.maxByOrNull { it.value } ?: IndexedValue(Int.MIN_VALUE, Int.MIN_VALUE)
        secondDigit = if (secondDigitIdx > secondWordDigit.value) line[secondDigitIdx].digitToInt() else secondWordDigit.index + 1

        firstDigit * 10 + secondDigit
    }

    measure { part1() }
    measure { part2() }
}
