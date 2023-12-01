package y2020

import measure
import readInput
import splitBy

fun main() {
    val answers by lazy {
        readInput("2020-6").splitBy { it.isEmpty() }
    }

    fun part1(): Int = answers.sumOf { it.joinToString("").toHashSet().size }

    fun part2(): Int {
        return answers.sumOf { groupAns ->
            groupAns.joinToString("").groupingBy { it }.eachCount().count { (_, count) -> count == groupAns.size }
        }
    }

    measure { part1() }
    measure { part2() }
}
