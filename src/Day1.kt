fun main() {
    val input by lazy {
        readInput("Day1")
    }

    fun getCalibration(digits: List<Int>): Int = digits.first() * 10 + digits.last()
    fun toDigits(s: String) = s.filter(Char::isDigit).map(Char::digitToInt)

    val digitWords = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine").mapIndexed { i, s -> s to i + 1 }.toMap()
    fun toDigitsWithWords(s: String): List<Int> {
        s.findAnyOf(digitWords.keys)
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

    fun part1(): Int = input.map(::toDigits).sumOf(::getCalibration)
    fun part2(): Int = input.map(::toDigitsWithWords).sumOf(::getCalibration)

    val allDigitWords = ((0..9).map(Int::toString) + listOf("#placeholder", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")).mapIndexed { i, s -> s to i % 10 }.toMap()
    fun part2Alternative() = input.sumOf {
        val firstDigit = it.findAnyOf(allDigitWords.keys)!!.second
        val lastDigit = it.findLastAnyOf(allDigitWords.keys)!!.second
        allDigitWords[firstDigit]!! * 10 + allDigitWords[lastDigit]!!
    }

    measure { part1() }
    measure { part2() }
    measure { part2Alternative() }
}
