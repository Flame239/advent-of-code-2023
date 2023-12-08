abstract class Hand(cards: String) : Comparable<Hand> {
    private val cardVals: List<Int> = cards.map { cardsStrength().indexOf(it) }
    private val handPower: Int = getHandPower(cardVals)

    abstract fun getHandPower(cards: List<Int>): Int
    abstract fun cardsStrength(): List<Char>

    override fun compareTo(other: Hand): Int {
        val handPowerComp = handPower.compareTo(other.handPower)
        return if (handPowerComp == 0) compareHandVals(cardVals, other.cardVals) else handPowerComp
    }

    private fun compareHandVals(cards1: List<Int>, cards2: List<Int>): Int {
        for (i in cards1.indices) {
            val comp = cards1[i].compareTo(cards2[i])
            if (comp != 0) return comp
        }
        return 0
    }

    protected fun getHandPowerByCount(count: Map<Int, Int>): Int {
        if (count.containsValue(5)) return 7
        if (count.containsValue(4)) return 6
        if (count.containsValue(3) && count.containsValue(2)) return 5
        if (count.containsValue(3)) return 4
        if (count.filterValues { it == 2 }.count() == 2) return 3
        if (count.filterValues { it == 2 }.count() == 1 && !count.containsValue(3)) return 2
        return 1
    }
}

class NormalHand(cards: String) : Hand(cards) {
    companion object {
        private val CARD_STRENGTH = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
    }

    override fun cardsStrength(): List<Char> = CARD_STRENGTH

    override fun getHandPower(cards: List<Int>): Int {
        val count: Map<Int, Int> = cards.groupingBy { it }.eachCount()
        return getHandPowerByCount(count)
    }
}

class JokerHand(cards: String) : Hand(cards) {
    companion object {
        private val CARDS_WITH_JOKER_STRENGTH = listOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')
    }

    override fun cardsStrength(): List<Char> = CARDS_WITH_JOKER_STRENGTH

    override fun getHandPower(cards: List<Int>): Int {
        val count = cards.groupingBy { it }.eachCount().toMutableMap()
        val jokers = count.getOrDefault(0, 0)
        if (jokers == 5) return 7
        count.remove(0)
        val maxKey = count.maxByOrNull { it.value }!!.key
        count[maxKey] = count[maxKey]!! + jokers

        return getHandPowerByCount(count)
    }
}
