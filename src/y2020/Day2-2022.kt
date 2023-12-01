package y2020

import measure
import readInput

fun main() {
    data class Password(val min: Int, val max: Int, val char: Char, val pass: String)

    val passwords by lazy {
        readInput("2020-2").map { s ->
            val (charLim, pass) = s.split(": ")
            val (lim, char) = charLim.split(" ")
            val (min, max) = lim.split("-").map { it.toInt() }
            Password(min, max, char[0], pass)
        }
    }

    fun part1(): Int = passwords.count { p ->
        IntRange(p.min, p.max).contains(p.pass.count { c -> c == p.char })
    }

    fun part2(): Int = passwords.count { p ->
        (p.pass[p.min - 1].toString() + p.pass[p.max - 1]).count { c -> c == p.char } == 1
    }

    measure { part1() }
    measure { part2() }
}
