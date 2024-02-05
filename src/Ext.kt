import kotlin.math.max

infix fun Int.toward(to: Int): IntProgression {
    val step = if (this > to) -1 else 1
    return IntProgression.fromClosedRange(this, to, step)
}

infix fun Int.towardExclusiveFrom(to: Int): IntProgression {
    val step = if (this > to) -1 else 1
    return IntProgression.fromClosedRange(this + step, to, step)
}

fun String.sortAlphabetically() = String(toCharArray().apply { sort() })

fun <T> List<T>.orderedPairs(): Sequence<Pair<T, T>> = sequence {
    for (i in 0 until size - 1) {
        for (j in 0 until size - 1) {
            if (i == j) continue
            yield(get(i) to get(j))
        }
    }
}

fun <T> List<T>.unorderedPairs(): Sequence<Pair<T, T>> = sequence {
    for (i in 0 until size - 1) {
        for (j in i + 1 until size) {
            yield(get(i) to get(j))
        }
    }
}

fun ClosedRange<Int>.intersect(other: ClosedRange<Int>) = !(start > other.endInclusive || endInclusive < other.start)

fun ClosedRange<Int>.contains(other: ClosedRange<Int>) = other.start >= start && other.endInclusive <= endInclusive

fun ClosedRange<Int>.count() = endInclusive - start + 1

fun Int.getMax(other: Int): Int {
    return max(this, other)
}

fun <T> MutableList<T>.swap(i: Int, j: Int) {
    val tmp = this[i]
    this[i] = this[j]
    this[j] = tmp
}

fun List<Long>.lcm() = lcmList(this)

fun <T> List<T>.splitBy(predicate: (T) -> Boolean): List<List<T>> =
        fold(mutableListOf(mutableListOf<T>())) { acc, t ->
            if (predicate(t)) acc.add(mutableListOf()) else acc.last().add(t)
            acc
        }.filterNot { it.isEmpty() }

fun List<Long>.mult(): Long = this.reduce(Long::times)

fun String.isNumber() = this.all { it.isDigit() }

fun Char.isHex() = this.isDigit() || this in 'a'..'f'

typealias IntArray2 = Array<IntArray>

typealias LongArray3 = Array<Array<LongArray>>

fun longArray3(n: Int, m: Int, k: Int, default: Long = 0) = Array(n) { Array(m) { LongArray(k) { default } } }

inline fun IntArray2.sumOfIndexed2(action: (i: Int, j: Int, x: Int) -> Long): Long {
    var sum = 0L
    for (i in indices) {
        val b = get(i)
        for (j in b.indices) {
            sum += action(i, j, b[j])
        }
    }
    return sum
}

fun List<Long>.diffs() = this.windowed(2).map { it[1] - it[0] }

fun Array<BooleanArray>.copy() = Array(size) { get(it).clone() }
