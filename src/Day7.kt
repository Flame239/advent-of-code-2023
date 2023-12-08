fun main() {
    data class HandBid(val hand: String, val bid: Long)

    val handbids by lazy {
        readInput("Day7").map { line -> line.split(" ").let { HandBid(it[0], it[1].toLong()) } }
    }

    fun getWinnings(toHand: (String) -> Hand): Long {
        return handbids.sortedBy { toHand(it.hand) }.withIndex().sumOf { (i, handbid) -> (i + 1) * handbid.bid }
    }

    fun part1(): Long = getWinnings { NormalHand(it) }

    fun part2(): Long = getWinnings { JokerHand(it) }

    measure { part1() }
    measure { part2() }
}
