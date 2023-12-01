fun main() {
    val input by lazy {
        readInput("Day1")
    }

    fun getCalibration(digits: List<Int>): Int = digits.first() * 10 + digits.last()
    fun toDigits(s: String) = s.filter { it.isDigit() }.map { it.digitToInt() }

    val digitWords = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine").mapIndexed { i, s -> s to i + 1 }.toMap()
    fun toDigitsWithWords(s: String): List<Int> {
        val digits = mutableListOf<Int>()
        var i = 0
        while (i < s.length) {
            if (s[i].isDigit()) {
                digits.add(s[i].digitToInt())
                i++
                continue
            }
            digitWords.entries.firstOrNull { s.startsWith(it.key, i) }?.also { digits.add(it.value) }
            i++
        }
        return digits
    }

    fun part1(): Int = input.map { toDigits(it) }.sumOf { getCalibration(it) }
    fun part2(): Int = input.map { toDigitsWithWords(it) }.sumOf { getCalibration(it) }

    measure { part1() }
    measure { part2() }
}
