fun main() {
    val seqs: List<List<Long>> by lazy {
        readInput("Day9").map { it.split(" ").map(String::toLong) }
    }

    fun nextNum(seq: List<Long>): Long {
        val diffs = mutableListOf(seq)
        var curDiffs = seq.diffs()
        while (!curDiffs.all { it == 0L }) {
            diffs.add(curDiffs)
            curDiffs = curDiffs.diffs()
        }
        return diffs.foldRight(0L) { d, acc -> acc + d.last() }
    }

    fun part1(): Long = seqs.sumOf { seq -> nextNum(seq) }

    fun part2(): Long = seqs.map { it.reversed().toMutableList() }.sumOf { seq -> nextNum(seq) }

    measure { part1() }
    measure { part2() }
}
