import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun main() {
    data class Race(val time: Long, val maxDist: Long)

    val races: List<Race> by lazy {
        val lines = readInput("Day6").map { it.substringAfter(":").split(Regex("\\s+")).filter(String::isNotBlank).map(String::toLong) }
        lines[0].mapIndexed { index, time -> Race(time, lines[1][index]) }
    }

    val singleRace by lazy {
        val lines = readInput("Day6").map { it.substringAfter(":").replace(" ", "").toLong() }
        Race(lines[0], lines[1])
    }

    fun beatTheRace(r: Race): Long {
        val a = -1.0
        val b = r.time
        val c = -r.maxDist
        val det = b * b - 4 * a * c

        val r1 = (-b + sqrt(det)) / (2 * a)
        val r2 = (-b - sqrt(det)) / (2 * a)

        return floor(r2).toLong() - ceil(r1).toLong() + 1
    }

    fun part1(): Long = races.map(::beatTheRace).also { println(it) }.mult()

    fun part2(): Long = beatTheRace(singleRace)

    measure { part1() }
    measure { part2() }
}
