import java.lang.StringBuilder

fun main() {
    data class Springs(val springs: String, val groups: List<Int>) {
        fun unfold(): Springs {
            val newS = StringBuilder(springs)
            val newGroups = mutableListOf<Int>()
            newGroups.addAll(groups)
            repeat(4) {
                newS.append('?').append(springs)
                newGroups.addAll(groups)
            }
            return Springs(newS.toString(), newGroups)
        }
    }

    val springs by lazy {
        readInput("Day12").map { l -> l.split(" ").let { split -> Springs(split[0], split[1].split(",").map { it.toInt() }) } }
    }

    // i - current spring index
    // j - current input group index
    // len - how many subsequent '#' we have until now (aka 'current' group)
    fun solve(s: Springs, i: Int, j: Int, len: Int, cache: LongArray3): Long {
        if (j >= s.groups.size && len > 0) return 0  // if no more groups left, but we have more # -> no good
        if (j < s.groups.size && s.groups[j] < len) return 0 // if len is too much -> do not proceed
        if (i >= s.springs.length) { // when processed all springs
            return if (j >= s.groups.size || (j == s.groups.size - 1 && s.groups.last() == len)) 1 else 0 // count only if we matched all the groups
        }
        if (cache[i][j][len] != -1L) return cache[i][j][len]

        var result = 0L
        val curS = s.springs[i]
        if (curS == '.' || curS == '?') {
            result += if (len == 0) { // no current group -> just move on
                solve(s, i + 1, j, 0, cache)
            } else { // we should 'close' current group and check if its size matches expected
                if (s.groups[j] == len) solve(s, i + 1, j + 1, 0, cache) else 0L
            }
        }

        if (curS == '#' || curS == '?') { // continue current group
            result += solve(s, i + 1, j, len + 1, cache)
        }

        cache[i][j][len] = result
        return result
    }

    fun countArrangements(s: Springs) = solve(s, 0, 0, 0, longArray3(s.springs.length + 1, s.groups.size + 1, s.groups.max() + 1, -1L))

    fun part1(): Long {
        return springs.sumOf(::countArrangements)
    }

    fun part2(): Long {
        return springs.map(Springs::unfold).sumOf(::countArrangements)
    }

    measure { part1() }
    measure { part2() }

}
