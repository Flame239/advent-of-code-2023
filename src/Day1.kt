fun main() {
    val input by lazy {
        readInput("Day1")
    }

    fun getCalibration(digits: List<Int>): Int = digits.first() * 10 + digits.last()
    fun toDigits(s: String) = s.filter(Char::isDigit).map(Char::digitToInt)

    val digitWords = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine").mapIndexed { i, s -> s to i + 1 }.toMap()
    fun toDigitsWithWords(s: String): List<Int> {
        val digits = mutableListOf<Int>()
        for (i in s.indices) {
            if (s[i].isDigit()) {
                digits.add(s[i].digitToInt())
            } else {
                digitWords.entries.firstOrNull { s.startsWith(it.key, i) }?.also { digits.add(it.value) }
            }
        }
        return digits
    }

    fun part1(): Int = input.map(::toDigits).sumOf(::getCalibration)
    fun part2(): Int = input.map(::toDigitsWithWords).sumOf(::getCalibration)

    val allDigitWords = ((0..9).map(Int::toString) + listOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"))
            .mapIndexed { i, s -> s to i % 10 }.toMap()

    fun part2Alternative() = input.sumOf {
        val firstDigit = it.findAnyOf(allDigitWords.keys)!!.second
        val lastDigit = it.findLastAnyOf(allDigitWords.keys)!!.second
        allDigitWords[firstDigit]!! * 10 + allDigitWords[lastDigit]!!
    }

    measure { part1() }
    measure { part2() }
    measure { part2Alternative() }
}
