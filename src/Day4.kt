fun main() {
    data class Scratchcard(val winningNumbers: Set<Int>, val numbers: Set<Int>) {
        val worth = numbers.count { winningNumbers.contains(it) }.let { matched -> if (matched == 0) 0 else 1 shl (matched - 1) }
        val matched = numbers.count { winningNumbers.contains(it) }
    }

    fun spaceDelimNumsToSet(s: String) = s.split(Regex("\\s+")).filter { it.isNotBlank() }.map(String::toInt).toHashSet()
    val cards by lazy {
        readInput("Day4").map { it.substringAfter(":") }.map { line -> line.split("|").let { Scratchcard(spaceDelimNumsToSet(it[0]), spaceDelimNumsToSet(it[1])) } }
    }

    fun part1(): Int = cards.sumOf { it.worth }
    fun part2(): Long {
        val copies = LongArray(cards.size) { 1 }
        for (i in cards.indices) {
            for (j in i + 1..i + cards[i].matched) {
                copies[j] += copies[i]
            }
        }
        return copies.sum()
    }

    measure { part1() }
    measure { part2() }
}
