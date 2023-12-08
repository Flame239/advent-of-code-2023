import java.io.File
import kotlin.math.min
import kotlin.system.measureNanoTime

fun readInput(name: String): List<String> = File("input", "$name.txt").readLines()

fun Any?.println() = println(this)

fun gcd(a: Long, b: Long): Long {
    if (b == 0L) return a
    return gcd(b, a % b)
}

fun lcm(a: Long, b: Long): Long {
    return a / gcd(a, b) * b
}

fun lcmList(input: List<Long>): Long {
    return input.reduce { a, b -> lcm(a, b) }
}

private val red = "\u001b[0;31m"
private val green = "\u001b[32m"
private val reset = "\u001b[0m"
fun measure(block: () -> Any) {
    var min: Long = Long.MAX_VALUE
    var result: Any = ""
    repeat(1) {
        measureNanoTime {
            result = block()
        }.also { min = min(min, it) }
    }
    println(red + result + reset)
    println(green + "⏳ " + min / 1000000 + " ms" + reset)
}

